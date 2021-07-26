package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreenActivity extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        username = getIntent().getStringExtra("USERNAME");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.homePage:
                    intent = new Intent(this, HomeScreenActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.profilePage:
                    intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
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

        Intent intent = new Intent(this, TopicSelection.class);
        intent.putExtra("objective", "quiz");
        intent.putExtra("username", username);
        startActivity(intent);
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