package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreenActivity extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        username = getIntent().getStringExtra("USERNAME");
    }

    public void onClickLearnButton(View view) {
        System.out.println("CLicked Learn button");

        Intent intent = new Intent(this, TopicSelection.class);
        intent.putExtra("objective", "learn");
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void onClickQuizButton(View view) {
        System.out.println("CLicked Quiz button");
    }

    public void onClickBehavioralPracticeButton(View view) {
        System.out.println("CLicked behavioralPractice button");

        Intent intent = new Intent(this, TopicSelection.class);
        intent.putExtra("objective", "behavioral");
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void onClickInterviewTipsButton(View view) {
        System.out.println("CLicked Interview tips button");
    }

    public void onClickResumeTipsButton(View view) {
        System.out.println("CLicked Resume Tips button");
    }

}