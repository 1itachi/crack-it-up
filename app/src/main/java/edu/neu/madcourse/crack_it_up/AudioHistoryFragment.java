package edu.neu.madcourse.crack_it_up;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
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
    private File audioFileCurrentlyPlaying = null;
    private SeekBar mediaPlayerSeekbar;
    private Handler seekBarHandler;
    private Runnable syncSeekBar;

    private ImageButton playButton, rewindButton, forwardButton;
    private TextView mediaPlayerHeader, mediaPlayerFileNameCurrentlyPlaying;


    public AudioHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        audioFiles = getAllAudioFiles();

        mediaPlayerSeekbar = view.findViewById(R.id.seekBar);
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

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCurrentlyPlaying) {
                    pauseAudio();
                } else {
                    if (audioFileCurrentlyPlaying != null) {
                        resumeAudio();
                    }
                }
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCurrentlyPlaying) {
                    pauseAudio();
                    int progress = mediaPlayerSeekbar.getProgress();
                    System.out.println("Current progress");
                    progress += 200;
                    mediaPlayerForAudioRecordings.seekTo(progress);
                    resumeAudio();
                }
            }
        });

        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCurrentlyPlaying) {
                    pauseAudio();
                    int progress = mediaPlayerSeekbar.getProgress();
                    System.out.println("Current progress");
                    progress -= 200;
                    mediaPlayerForAudioRecordings.seekTo(progress);
                    resumeAudio();
                }
            }
        });

        mediaPlayerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pauseAudio();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mediaPlayerForAudioRecordings.seekTo(progress);
                resumeAudio();
            }
        });
    }

    private File[] getAllAudioFiles() {
        BehavioralAudioRecordActivity activity = (BehavioralAudioRecordActivity) getActivity();
        String questionId = activity.getQuestionId();
        audioFilePath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        audioDirectory = new File(audioFilePath + "/" + questionId);
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
        mediaPlayerHeader.setText(R.string.playing);

        audioFileCurrentlyPlaying = file;
        if (isCurrentlyPlaying) {
            stopAudioOnMediaPlayer();
            playAudioOnMediaPlayer(audioFileCurrentlyPlaying);
        } else {
            System.out.println("Playing " + audioFileCurrentlyPlaying.getName());
            playAudioOnMediaPlayer(audioFileCurrentlyPlaying);
        }
    }

    private void stopAudioOnMediaPlayer() {
        playButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_play_filled_circle));
        mediaPlayerHeader.setText(R.string.stopped);
        isCurrentlyPlaying = false;
        mediaPlayerForAudioRecordings.stop();
        seekBarHandler.removeCallbacks(syncSeekBar);
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

        mediaPlayerHeader.setText(R.string.playing);
        playButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_pause_circle_filled));
        mediaPlayerFileNameCurrentlyPlaying.setText(audioFileCurrentlyPlaying.getName());

        mediaPlayerSeekbar.setMax(mediaPlayerForAudioRecordings.getDuration());
        seekBarHandler = new Handler();
        updateRunnable();
        seekBarHandler.postDelayed(syncSeekBar, 0);

        mediaPlayerForAudioRecordings.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudioOnMediaPlayer();
                mediaPlayerHeader.setText(R.string.finished);
                mediaPlayerSeekbar.setProgress(mediaPlayerForAudioRecordings.getDuration());
            }
        });
    }

    private void updateRunnable() {
        syncSeekBar = new Runnable() {
            @Override
            public void run() {
                mediaPlayerSeekbar.setProgress(mediaPlayerForAudioRecordings.getCurrentPosition());
                seekBarHandler.postDelayed(this, 500);
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if(isCurrentlyPlaying) {
            stopAudioOnMediaPlayer();
        }
    }

    private void pauseAudio() {
        mediaPlayerForAudioRecordings.pause();
        mediaPlayerHeader.setText(R.string.paused);
        playButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_play_filled_circle));
        seekBarHandler.removeCallbacks(syncSeekBar);
        isCurrentlyPlaying = false;
    }

    private void resumeAudio() {
        mediaPlayerForAudioRecordings.start();
        mediaPlayerHeader.setText(R.string.playing);
        playButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_pause_circle_filled));
        isCurrentlyPlaying = true;

        updateRunnable();
        seekBarHandler.postDelayed(syncSeekBar, 0);
    }
}