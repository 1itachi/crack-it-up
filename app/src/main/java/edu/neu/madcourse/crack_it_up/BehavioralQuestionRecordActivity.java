package edu.neu.madcourse.crack_it_up;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class BehavioralQuestionRecordActivity extends AppCompatActivity{
    private String username, topicName, questionText;
    private TextView topicNameTextView, questionTextView;
    private Button exampleAnswerButton, recordingHistoryButton;
    private int questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral_question_record);

        // Setting the text views
        username = getIntent().getStringExtra("USERNAME");
        topicName = getIntent().getStringExtra("TOPIC_NAME");
        questionText = getIntent().getStringExtra("QUESTION_TEXT");
//      questionId = Integer.parseInt(getIntent().getStringExtra("QUESTION_ID"));

        topicNameTextView = findViewById(R.id.behavioralTopicTextView);
        topicNameTextView.setText(topicName);

        questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(questionText);

        // Setting the bottom Navigation view
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

        // Setting listeners on dialog buttons
        exampleAnswerButton = findViewById(R.id.viewExampleAnswersButton);
        exampleAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogExampleAnswer();
            }
        });

        recordingHistoryButton = findViewById(R.id.viewHistoryButton);
        recordingHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogRecordingHistory();
            }
        });

    }

    private void openDialogRecordingHistory() {
        //to do
    }

    private void openDialogExampleAnswer() {
        Bundle args = new Bundle();
        args.putString("answer", "This is a sample answer");

        BehavioralExampleAnswerDialog dialog = new BehavioralExampleAnswerDialog();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "example answer");
    }

}