package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
    }

    public void onClickLearnButton(View view) {
        Intent chooseTopicIntent = new Intent(HomeScreenActivity.this, ChooseTopic.class);
        // Start the new activity
        startActivity(chooseTopicIntent);
    }

    public void onClickQuizButton(View view) {
        System.out.println("CLicked Quiz button");
    }

    public void onClickBehavioralPracticeButton(View view) {
        System.out.println("CLicked behavioralPractice button");
        Intent topicIntent = new Intent(HomeScreenActivity.this, BehavioralTopicSelectionActivity.class);
        startActivity(topicIntent);
    }

    public void onClickInterviewTipsButton(View view) {
        System.out.println("CLicked Interview tips button");
    }

    public void onClickResumeTipsButton(View view) {
        System.out.println("CLicked Resume Tips button");
    }

}