package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class BehavioralAudioRecordActivity extends AppCompatActivity{
    private String username, topicName, questionText, answer, questionId, attemptedByUser;
    private TextView topicNameTextView, questionTextView;
    private Button exampleAnswerButton;
    private File[] audioFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral_audio_record);

        // Setting the text views
        username = getIntent().getStringExtra("USERNAME");
        topicName = getIntent().getStringExtra("TOPIC_NAME");
        questionText = getIntent().getStringExtra("QUESTION_TEXT");
        answer = getIntent().getStringExtra("ANSWER");
        questionId = getIntent().getStringExtra("QUESTION_ID");
        attemptedByUser = getIntent().getStringExtra("ATTEMPTED_BY_USER");

        topicNameTextView = findViewById(R.id.behavioralTopicTextView);
        topicNameTextView.setText(topicName);

        questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(questionText);

        // Setting listeners on dialog buttons
        exampleAnswerButton = findViewById(R.id.idealAnswerButton);
        if(attemptedByUser.equals("true")) {
            exampleAnswerButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_open_24, 0, 0, 0);
        }
        exampleAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionId = getQuestionId();
                String audioFilePath = getExternalFilesDir("/").getAbsolutePath();
                File audioDirectory = new File(audioFilePath + "/" + questionId);
                audioFiles = audioDirectory.listFiles();

                if (audioFiles == null || audioFiles.length==0) {
                    Toast.makeText(BehavioralAudioRecordActivity.this, "Record at least one answer to unlock the history ", Toast.LENGTH_LONG).show();
                } else {
                    openDialogExampleAnswer(answer);
                }
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

    public String getQuestionId() {
        return questionId;
    }

    public void setUnlockImage() {
        exampleAnswerButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_open_24, 0, 0, 0);
    }
}