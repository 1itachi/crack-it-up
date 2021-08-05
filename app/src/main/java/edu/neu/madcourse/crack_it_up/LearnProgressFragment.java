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
 * Use the {@link LearnProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnProgressFragment extends Fragment {

    private RecyclerView recyclerViewUserProgress;
    private RecyclerViewAdapterUserProgress recyclerViewAdapterUserProgress;
    private List<UserScore> userScores;

    public LearnProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userScores = getQuizScoresForUser();

        recyclerViewUserProgress = view.findViewById(R.id.recyclerViewUserProgress);
        recyclerViewAdapterUserProgress = new RecyclerViewAdapterUserProgress(userScores);
        recyclerViewUserProgress.setHasFixedSize(true);
        recyclerViewUserProgress.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUserProgress.setAdapter(recyclerViewAdapterUserProgress);
    }

    private List<UserScore> getQuizScoresForUser() {
        List<UserScore> userScores = new ArrayList<>();
        userScores.add(new UserScore("topic1Arrays", 70));
        userScores.add(new UserScore("topic2Strings", 30));
        // for every topic user has not attempted  a quiz, give score as -99

        return userScores;
    }

    public static LearnProgressFragment newInstance() {
        LearnProgressFragment fragment = new LearnProgressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }
}