package edu.neu.madcourse.crack_it_up;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioHistoryFragment extends Fragment implements RecyclerViewAdapterAudioHistory.RecordingListener{
    private ConstraintLayout mediaPlayer;
    private BottomSheetBehavior bottomSheetBehavior;
    private File[] audioFiles;
    private File audioDirectory;
    String audioFilePath;

    private RecyclerView recyclerViewAudioHistory;
    private RecyclerViewAdapterAudioHistory recyclerViewAdapterAudioHistory;

    private MediaPlayer mediaPlayerForAudioRecordings;
    private boolean isCurrentlyPlaying;
    private File audioFileCurrentlyPlaying;

    private ImageButton playButton, rewindButton, forwardButton;
    private TextView mediaPlayerHeader, mediaPlayerFileNameCurrentlyPlaying;


    public AudioHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        audioFiles = getAllAudioFiles();

        playButton = view.findViewById(R.id.playImageView);
        rewindButton = view.findViewById(R.id.rewindImageView);
        forwardButton = view.findViewById(R.id.forwardImageView);
        mediaPlayerHeader = view.findViewById(R.id.headerTitle);
        mediaPlayerFileNameCurrentlyPlaying = view.findViewById(R.id.audioFileNameTextView);


        mediaPlayer = view.findViewById(R.id.playAudioPanel);
        bottomSheetBehavior = BottomSheetBehavior.from(mediaPlayer);
        recyclerViewAudioHistory = view.findViewById(R.id.recyclerViewAudioHistory);

        recyclerViewAdapterAudioHistory = new RecyclerViewAdapterAudioHistory(audioFiles, this);
        recyclerViewAudioHistory.setHasFixedSize(true);
        recyclerViewAudioHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAudioHistory.setAdapter(recyclerViewAdapterAudioHistory);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private File[] getAllAudioFiles() {
        audioFilePath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        audioDirectory = new File(audioFilePath);
        return audioDirectory.listFiles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_history, container, false);
    }

    @Override
    public void onAudioClick(File file, int position) {
        System.out.println("Recording row clicked");
        System.out.println("Playing " + file.getName());
        mediaPlayerHeader.setText("Finished");


        if (isCurrentlyPlaying) {
            stopAudioOnMediaPlayer();
            playAudioOnMediaPlayer(audioFileCurrentlyPlaying);
        } else {
            audioFileCurrentlyPlaying = file;
            System.out.println("Playing " + audioFileCurrentlyPlaying.getName());
            playAudioOnMediaPlayer(audioFileCurrentlyPlaying);
        }
    }

    private void stopAudioOnMediaPlayer() {
        playButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.play));
        isCurrentlyPlaying = false;
    }

    private void playAudioOnMediaPlayer(File audioFile) {
        mediaPlayerForAudioRecordings = new MediaPlayer();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        try {
            mediaPlayerForAudioRecordings.setDataSource(audioFileCurrentlyPlaying.getAbsolutePath());
            mediaPlayerForAudioRecordings.prepare();
            mediaPlayerForAudioRecordings.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isCurrentlyPlaying = true;

        mediaPlayerHeader.setText("Playing");
        playButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.record));
        mediaPlayerFileNameCurrentlyPlaying.setText(audioFileCurrentlyPlaying.getName());

        mediaPlayerForAudioRecordings.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudioOnMediaPlayer();
                mediaPlayerHeader.setText("Finished");
            }
        });
    }

    @Override
    public void onPlayButtonClick(File file, int position) {
        System.out.println("Play button clicked");
    }
}