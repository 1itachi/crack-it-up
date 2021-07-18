package edu.neu.madcourse.crack_it_up;

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
        System.out.println("CLicked Learn button");
    }

    public void onClickQuizButton(View view) {
        System.out.println("CLicked Quiz button");
    }

    public void onClickBehavioralPracticeButton(View view) {
        System.out.println("CLicked behavioralPractice button");
    }

    public void onClickInterviewTipsButton(View view) {
        System.out.println("CLicked Interview tips button");
    }

    public void onClickResumeTipsButton(View view) {
        System.out.println("CLicked Resume Tips button");
    }

}