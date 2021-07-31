package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TopicSelection extends AppCompatActivity implements RecyclerViewAdapterLearnTopicsSelection.TopicListener {

    private RecyclerView recyclerViewForAllTopics;
    private RecyclerView.LayoutManager recyclerViewLayoutManger;
    private RecyclerViewAdapterLearnTopicsSelection recyclerViewAdapter;
    private ArrayList<TopicCard> topicCards = new ArrayList<>();
    private String username, objective;
    private DatabaseReference mDatabase;
    private DatabaseReference mTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.homePage);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.homePage:
                    intent = new Intent(this, HomeScreenActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.profilePage:
                    intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        username = getIntent().getStringExtra("username");
        objective = getIntent().getStringExtra("objective");


       getTopicsFromFirebase();

        //recycler view
        recyclerViewForAllTopics = findViewById(R.id.recyclerViewAllTopics);
        recyclerViewLayoutManger = new GridLayoutManager(this, 2);
        recyclerViewForAllTopics.setLayoutManager(recyclerViewLayoutManger);

        recyclerViewAdapter = new RecyclerViewAdapterLearnTopicsSelection(topicCards, this);
        recyclerViewForAllTopics.setAdapter(recyclerViewAdapter);
    }

    private ArrayList<TopicCard> getTopicsFromFirebase() {
        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //get user reference
        mTopic = mDatabase.child("topic");

        mTopic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                topicCards.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    TopicCard topic = snapshot1.getValue(TopicCard.class);

                    if (objective.equals("behavioral") && topic.getType().equals("behavioral")) {

                        topicCards.add(topic);

                    }else if( (objective.equals("learn") || objective.equals("quiz")) && topic.getType().equals("learn")){
                        topicCards.add(topic);
                    }
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) { }
        });

        return topicCards;
    }

    @Override
    public void onTopicClick(int position) {
        System.out.println("Topic clicked at position " + position);

        Intent intent;
        if (objective.equals("behavioral")) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, ScreenSlideActivity.class);
        }

        intent.putExtra("TOPIC_NAME", topicCards.get(position).getName());
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

    @Override
    public void onTopicButtonClick(int position) {
        System.out.println("Topic clicked at position " + position + " for objective = " + objective);

        Intent intent;
        if (objective.equals("behavioral")) {
            intent = new Intent(this, BehavioralQuestionListActivity.class);
        } else if (objective.equals("learn")) {
            intent = new Intent(this, ScreenSlideActivity.class);
        } else if (objective.equals("quiz")) {
            intent = new Intent(this, ScreenSlideActivity.class);
        } else {
            intent = new Intent(this, ScreenSlideActivity.class);
        }

        intent.putExtra("TOPIC_NAME", topicCards.get(position).getName());
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
}