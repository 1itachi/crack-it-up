package edu.neu.madcourse.crack_it_up;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class RecordFragment extends Fragment implements View.OnClickListener {

    private NavController navController;
    private ImageView historyButton;
    private ImageButton recordingListButton;
    private MaterialButton recordButton;
    private boolean currentlyRecording = false;
    private String recordPermission = Manifest.permission.RECORD_AUDIO, audioFilePath, audioFileName, questionId;
    private int permissionCode = 1;
    private MediaRecorder mediaRecorder;
    private Chronometer chronometer;
    private TextView recordingFileName;

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        historyButton = view.findViewById(R.id.recordHistoryImageButton);
        historyButton.setOnClickListener(this);
        recordButton = view.findViewById(R.id.recordButton);
        recordButton.setOnClickListener(this);
        chronometer = view.findViewById(R.id.audio_record_timer);
        recordingFileName = view.findViewById(R.id.fileNameTextView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recordHistoryImageButton:
                BehavioralAudioRecordActivity activity = (BehavioralAudioRecordActivity) getActivity();
                String questionId = activity.getQuestionId();
                String filePath = getActivity().getExternalFilesDir("/").getAbsolutePath();
                File audioDirectory = new File(filePath + "/" + questionId);
                File[] audioFiles = audioDirectory.listFiles();

                if (audioFiles == null || audioFiles.length==0) {
                    alertUserNoRecordings();
                } else {
                    if (currentlyRecording) {
                        alertUser();
                    } else {
                        navController.navigate(R.id.action_recordFragment_to_audioHistoryFragment);
                    }
                }
                break;
            case R.id.recordButton:
                System.out.println("User clicked record button");
                if (currentlyRecording) {
                    System.out.println("Closing the recording");
                    stopRecording();
                    recordButton.setIcon(getResources().getDrawable(R.drawable.record, null));
                    currentlyRecording = false;
                } else {
                    //check permission
                    System.out.println("Checking permission");
                    if (hasPermission()) {
                        System.out.println("Starting the recording");
                        startRecording();
                        recordButton.setIcon(getResources().getDrawable(R.drawable.ic_stop, null));
                        currentlyRecording = true;
                    }
                }
                break;

        }
    }

    private void alertUserNoRecordings() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("No recordings");
        alertDialog.setMessage("There are no recordings yet, click on the mic button to start recording");
        alertDialog.create();
        alertDialog.show();
    }

    private void alertUser() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Answer is still recording");
        alertDialog.setMessage("Are you sure you want to stop the recording and move to the recording history screen");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navController.navigate(R.id.action_recordFragment_to_audioHistoryFragment);
                currentlyRecording = false;
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (DialogInterface.OnClickListener) null);
        alertDialog.create();
        alertDialog.show();
    }

    private void startRecording() {
        audioFilePath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        createFolderForQuestionId();

        System.out.println("Audio file path " + audioFilePath);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.US);
        Date now = new Date();
        audioFileName = "Recording_" + formatter.format(now) + ".3gp";
        recordingFileName.setText("Recording, file name: " + audioFileName);
        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(audioFilePath + "/" + questionId + "/" + audioFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        mediaRecorder.start();
    }

    private void createFolderForQuestionId() {
        BehavioralAudioRecordActivity activity = (BehavioralAudioRecordActivity) getActivity();
        questionId = activity.getQuestionId();
        String dirPath = getActivity().getExternalFilesDir("/").getAbsolutePath() + "/" + questionId;
        File projDir = new File(dirPath);

        boolean success = false;
        if (!projDir.exists()) {
            success = projDir.mkdirs();
        } else {
            success= true;
        }

        if(!success) {
            System.out.println("Error creating storage folder for questionId");
            throw  new RuntimeException();
        }
    }

    private void stopRecording() {
        chronometer.stop();
        mediaRecorder.stop();
        recordingFileName.setText("Recording stopped, file saved: " + audioFileName);
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private boolean hasPermission() {
        if(ActivityCompat.checkSelfPermission(getContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("App has permission");
            return true;
        } else {
            System.out.println("App does not have permission");
            ActivityCompat.requestPermissions(getActivity(), new String[] {recordPermission}, permissionCode);
            return false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(currentlyRecording) {
            stopRecording();
        }
    }
}