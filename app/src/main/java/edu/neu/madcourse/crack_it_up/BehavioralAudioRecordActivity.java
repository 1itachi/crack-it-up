package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BehavioralAudioRecordActivity extends AppCompatActivity{
    private String username, topicName, questionText, answer;
    private TextView topicNameTextView, questionTextView;
    private int questionId;
    private Button exampleAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral_audio_record);

        // Setting the text views
        username = getIntent().getStringExtra("USERNAME");
        topicName = getIntent().getStringExtra("TOPIC_NAME");
        questionText = getIntent().getStringExtra("QUESTION_TEXT");
        answer = getIntent().getStringExtra("ANSWER");
      //questionId = Integer.parseInt(getIntent().getStringExtra("QUESTION_ID"));

        topicNameTextView = findViewById(R.id.behavioralTopicTextView);
        topicNameTextView.setText(topicName);

        questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(questionText);

        // Setting listeners on dialog buttons
        exampleAnswerButton = findViewById(R.id.idealAnswerButton);
        exampleAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogExampleAnswer(answer);
            }
        });

    }

    private void openDialogExampleAnswer(String answer) {
        Bundle args = new Bundle();
        args.putString("answer", answer);

        BehavioralExampleAnswerDialog dialog = new BehavioralExampleAnswerDialog();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "example answer");
    }
}