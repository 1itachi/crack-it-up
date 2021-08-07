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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private List<UserScore> userScores = new ArrayList<>();
    private List<TopicCard> topicCards = new ArrayList<>();
    private DatabaseReference mDatabase;
    private DatabaseReference mTopic, mUserScore;

    public LearnProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();
        String email = user.getEmail();
        String formattedEmail = email.replace("@", "").replace(".", "");

        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mTopic = mDatabase.child("topic");
        mUserScore = mDatabase.child("quizScores").child(formattedEmail);

        //fetch data from firebase
        getTopicsFromFirebase();
        getUserScoresFromFirebase();

        recyclerViewUserProgress = view.findViewById(R.id.recyclerViewUserProgress);
        recyclerViewAdapterUserProgress = new RecyclerViewAdapterUserProgress(userScores);
        recyclerViewUserProgress.setHasFixedSize(true);
        recyclerViewUserProgress.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUserProgress.setAdapter(recyclerViewAdapterUserProgress);
    }

    private void getUserScoresFromFirebase() {
        mUserScore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String topicId;
                int score;
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    topicId = snapshot1.getKey();
                    try{
                        //Integer -> int
                        System.out.println(snapshot1.getValue());
                        score = Integer.parseInt(snapshot1.getValue().toString());
                    } catch (NullPointerException e){
                        System.out.println(e);
                        score = -99;
                    }

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

                    if (topic.getType().equals("learn")) {
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