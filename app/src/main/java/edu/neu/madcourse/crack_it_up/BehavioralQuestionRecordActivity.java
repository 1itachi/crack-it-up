package edu.neu.madcourse.crack_it_up;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BehavioralQuestionRecordActivity extends AppCompatActivity{
    private String username, topicName, questionText;
    private TextView topicNameTextView, questionTextView;
    private int questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral_question_record);

        username = getIntent().getStringExtra("USERNAME");
        topicName = getIntent().getStringExtra("TOPIC_NAME");
        questionText = getIntent().getStringExtra("QUESTION_TEXT");
//        questionId = Integer.parseInt(getIntent().getStringExtra("QUESTION_ID"));

        topicNameTextView = findViewById(R.id.behavioralTopicTextView);
        topicNameTextView.setText(topicName);

        questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(questionText);
    }

}