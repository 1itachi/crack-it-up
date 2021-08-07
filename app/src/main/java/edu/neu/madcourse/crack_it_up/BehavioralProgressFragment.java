package edu.neu.madcourse.crack_it_up;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BehavioralProgressFragment extends Fragment {
    private RecyclerView recyclerViewUserProgress;
    private RecyclerViewAdapterUserProgress recyclerViewAdapterUserProgress;
    private List<UserScore> userScores = new ArrayList<>();
    private List<TopicCard> topicCards = new ArrayList<>();
    private Map<String, List<String>> questionsPerTopic = new HashMap<>();
    private DatabaseReference mDatabase;
    private DatabaseReference mTopic, mBehavioralQuestion;
    private List<String> userAnsweredQuestions;

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
        getUserAnsweredQuestions();

        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mTopic = mDatabase.child("topic");
        mBehavioralQuestion = mDatabase.child("behavioralQuestion");

        //fetch data from firebase
        getTopicsFromFirebase();
        getBehavioralQuestionsFromFirebase();

        recyclerViewUserProgress = view.findViewById(R.id.recyclerViewUserProgress);
        recyclerViewAdapterUserProgress = new RecyclerViewAdapterUserProgress(userScores);
        recyclerViewUserProgress.setHasFixedSize(true);
        recyclerViewUserProgress.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUserProgress.setAdapter(recyclerViewAdapterUserProgress);
    }

    private void getUserAnsweredQuestions() {
        String audioFilePath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        File audioDirectory = new File(audioFilePath);
        userAnsweredQuestions = new ArrayList<>();
        for(File file : audioDirectory.listFiles()){
            userAnsweredQuestions.add(file.getName());
        }
        System.out.println(userAnsweredQuestions);
    }

    private void getBehavioralQuestionsFromFirebase() {
        mBehavioralQuestion.addValueEventListener(new ValueEventListener() {
            String topicId;
            int score;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getUserAnsweredQuestions();
                for(DataSnapshot topicSnapshot : snapshot.getChildren()){
                    topicId = topicSnapshot.getKey();

                    int attempted = 0, notAttempted = 0;
                    for(DataSnapshot question : topicSnapshot.getChildren()) {
                        String questionId = question.getKey();
                        if(userAnsweredQuestions.contains(questionId)) {
                            attempted += 1;
                        } else {
                            notAttempted += 1;
                        }
                    }
                    score = Math.round(attempted/(attempted + notAttempted)) * 100;

                    for (UserScore userScore: userScores) {
                        if(userScore.getTopicId().equals(topicId)) {
                            userScore.setScore(score);
                        }
                    }
                }
                recyclerViewAdapterUserProgress.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void getTopicsFromFirebase() {
        mTopic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topicCards.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    TopicCard topic = snapshot1.getValue(TopicCard.class);

                    if (topic.getType().equals("behavioral")) {
                        topicCards.add(topic);
                    }
                }
                for(TopicCard topicCard: topicCards){
                    UserScore userScore = new UserScore(topicCard.getTopicId(), topicCard.getName(), -99);
                    userScores.add(userScore);
                }
                //getUserScoresFromFirebase();
                recyclerViewAdapterUserProgress.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
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