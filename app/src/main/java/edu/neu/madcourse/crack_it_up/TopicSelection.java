package edu.neu.madcourse.crack_it_up;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

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
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_topic_selection);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        captureOrientationChange();

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
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerViewLayoutManger = new GridLayoutManager(this, 2);
        } else {
            recyclerViewLayoutManger = new GridLayoutManager(this, 3);
        }
        recyclerViewForAllTopics.setLayoutManager(recyclerViewLayoutManger);

        recyclerViewAdapter = new RecyclerViewAdapterLearnTopicsSelection(topicCards, this);
        recyclerViewForAllTopics.setAdapter(recyclerViewAdapter);
    }

    private void getTopicsFromFirebase() {
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

    }

    @Override
    public void onTopicClick(int position) {
        System.out.println("Topic clicked at position " + position);

        Intent intent;
        if (objective.equals("behavioral")) {
            intent = new Intent(this, MainActivity.class);
        } else if (objective.equals("quiz")) {
            intent = new Intent(this, QuizActivity.class);
        }
        else {
            intent = new Intent(this, ScreenSlideActivity.class);
        }

        intent.putExtra("TOPIC_ID", topicCards.get(position).getTopicId());
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
            intent = new Intent(this, QuizActivity.class);
        } else {
            intent = new Intent(this, ScreenSlideActivity.class);
        }

        intent.putExtra("TOPIC_ID", topicCards.get(position).getTopicId());
        intent.putExtra("TOPIC_NAME", topicCards.get(position).getName());
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

    private void captureOrientationChange() {
        SensorEventListener m_sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    recyclerViewLayoutManger = new GridLayoutManager(context, 2);
                }
                else {
                    recyclerViewLayoutManger = new GridLayoutManager(context, 3);
                }
                recyclerViewForAllTopics.setLayoutManager(recyclerViewLayoutManger);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        SensorManager sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sm.registerListener(m_sensorEventListener, sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);

    }
}