package edu.neu.madcourse.crack_it_up;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;


public class RecordFragment extends Fragment implements View.OnClickListener {

    private NavController navController;
    private ImageView historyButton;
    private ImageButton recordingListButton;
    private MaterialButton recordButton;
    private boolean currentlyRecording = false;

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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recordHistoryImageButton:
                navController.navigate(R.id.action_recordFragment_to_audioHistoryFragment);
                break;
            case R.id.recordButton:
                if (currentlyRecording) {
                    recordButton.setIcon(getResources().getDrawable(R.drawable.app_icon, null));
                    currentlyRecording = false;
                } else {
                    recordButton.setIcon(getResources().getDrawable(R.drawable.record, null));
                    currentlyRecording = true;
                }
                break;

        }
    }
}