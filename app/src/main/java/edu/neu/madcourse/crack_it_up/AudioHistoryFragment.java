package edu.neu.madcourse.crack_it_up;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;

public class AudioHistoryFragment extends Fragment implements RecyclerViewAdapterAudioHistory.RecordingListener{
    private ConstraintLayout mediaPlayer;
    private BottomSheetBehavior bottomSheetBehavior;
    private File[] audioFiles;
    private File audioDirectory;
    String audioFilePath;

    private RecyclerView recyclerViewAudioHistory;
    private RecyclerViewAdapterAudioHistory recyclerViewAdapterAudioHistory;

    public AudioHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        audioFiles = getAllAudioFiles();

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
    public void onAudioClick(int position) {
        System.out.println("Recording row clicked");
    }

    @Override
    public void onPlayButtonClick(int position) {
        System.out.println("Play button clicked");
    }
}