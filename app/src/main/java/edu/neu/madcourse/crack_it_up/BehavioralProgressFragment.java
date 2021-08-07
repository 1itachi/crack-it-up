package edu.neu.madcourse.crack_it_up;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BehavioralProgressFragment extends Fragment {
    private RecyclerView recyclerViewUserProgress;
    private RecyclerViewAdapterUserProgress recyclerViewAdapterUserProgress;
    private List<UserScore> userScores;

    public BehavioralProgressFragment() {
        // Required empty public constructor
    }

    public static BehavioralProgressFragment newInstance() {
        BehavioralProgressFragment fragment = new BehavioralProgressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userScores = getPercentQuestionsCompletedPerTopic();

        recyclerViewUserProgress = view.findViewById(R.id.recyclerViewUserProgress);
        recyclerViewAdapterUserProgress = new RecyclerViewAdapterUserProgress(userScores);
        recyclerViewUserProgress.setHasFixedSize(true);
        recyclerViewUserProgress.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUserProgress.setAdapter(recyclerViewAdapterUserProgress);
    }

    private List<UserScore> getPercentQuestionsCompletedPerTopic() {
        List<UserScore> userScores = new ArrayList<>();
        userScores.add(new UserScore("topic3ProblemSolving", "Problem Solving", 20));
        // for every topic user has not attempted  a quiz, give score as -99

        return userScores;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_behavioral, container, false);
    }
}