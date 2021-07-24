package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BehavioralPracticeActivity extends AppCompatActivity {
    private String username;
    private String topicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral_practice);

        username = getIntent().getStringExtra("username");
        topicName = getIntent().getStringExtra("username");

    }

}