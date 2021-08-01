package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView questions;
    private TextView question;

    private AppCompatButton option1, option2, option3, option4;
    private AppCompatButton nextBtn;

    private List<QuizCard> questionList = new ArrayList<>();

    private int currentQuestionPosition = 0;

    private String selectedOptionByUser = "";


    private DatabaseReference mDatabase;
    private DatabaseReference mQuiz;

    private int userscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide_test);

        //set initial score to 0;
        userscore = 0;

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView selectedTopicName = findViewById(R.id.topicName);

        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        nextBtn = findViewById(R.id.nextBtn);

        final String getSelectedTopicName = getIntent().getStringExtra("TOPIC_NAME");

        selectedTopicName.setText(getSelectedTopicName);


        //firebase
        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //get topic id
        String topicId = getIntent().getStringExtra("TOPIC_ID");
        //get user reference
        mQuiz = mDatabase.child("question").child(topicId);



        mQuiz.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    QuizCard quiz = snapshot1.getValue(QuizCard.class);
                    questionList.add(quiz);

                }
                questions.setText((currentQuestionPosition+1)+"/"+questionList.size());
                question.setText(questionList.get(0).getQuestionText());
                option1.setText(questionList.get(0).getOption1());
                option2.setText(questionList.get(0).getOption2());
                option3.setText(questionList.get(0).getOption3());
                option4.setText(questionList.get(0).getOption4());
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option1.getText().toString();
                    option1.setBackgroundResource(R.drawable.round_back_red10);
                    option1.setTextColor(Color.WHITE);
                    if(questionList.get(currentQuestionPosition).getCorrectAnsw().equals(selectedOptionByUser)){
                        userscore++;
                    }
                    revealAnswer();

                    questionList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option2.getText().toString();
                    option2.setBackgroundResource(R.drawable.round_back_red10);
                    option2.setTextColor(Color.WHITE);
                    if(questionList.get(currentQuestionPosition).getCorrectAnsw().equals(selectedOptionByUser)){
                        userscore++;
                    }
                    revealAnswer();

                    questionList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option3.getText().toString();
                    option3.setBackgroundResource(R.drawable.round_back_red10);
                    option3.setTextColor(Color.WHITE);
                    if(questionList.get(currentQuestionPosition).getCorrectAnsw().equals(selectedOptionByUser)){
                        userscore++;
                    }
                    revealAnswer();

                    questionList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option4.getText().toString();
                    option4.setBackgroundResource(R.drawable.round_back_red10);
                    option4.setTextColor(Color.WHITE);
                    if(questionList.get(currentQuestionPosition).getCorrectAnsw().equals(selectedOptionByUser)){
                        userscore++;
                    }
                    revealAnswer();

                    questionList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;;
                if (selectedOptionByUser.isEmpty()) {
                    Toast.makeText(QuizActivity.this, "Please select an option",Toast.LENGTH_SHORT).show();
                }else if(b.getText().toString().equals("Submit Quiz")){
                    Intent intent = new Intent(QuizActivity.this, QuizResults.class);
                    int size = questionList.size();
                    int finalScore = (int) Math.round(userscore * 100.0/size);
                    intent.putExtra("percentage", String.valueOf(finalScore));
                    startActivity(intent);
                    finish();
                }
                else {
                    nextQuestion();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizActivity.this, TopicSelection.class));
                finish();
            }
        });
    }

    private void nextQuestion() {
        currentQuestionPosition++;
        if((currentQuestionPosition+1) == questionList.size()) {
            nextBtn.setText("Submit Quiz");
        }

        if (currentQuestionPosition < questionList.size()) {

            selectedOptionByUser = "";

            option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option1.setTextColor(Color.parseColor("#1F6BB8"));

            option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));

            option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));

            option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            questions.setText((currentQuestionPosition+1)+"/"+questionList.size());
            question.setText(questionList.get(currentQuestionPosition).getQuestionText());
            option1.setText(questionList.get(currentQuestionPosition).getOption1());
            option2.setText(questionList.get(currentQuestionPosition).getOption2());
            option3.setText(questionList.get(currentQuestionPosition).getOption3());
            option4.setText(questionList.get(currentQuestionPosition).getOption4());
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuizActivity.this, TopicSelection.class));
        finish();
    }

    private void revealAnswer(){
        final String getAnswer = questionList.get(currentQuestionPosition).getCorrectAnsw();

        if (option1.getText().toString().equals(getAnswer)) {
            option1.setBackgroundResource(R.drawable.round_back_green10);
            option1.setTextColor(Color.WHITE);
        }
        else if (option2.getText().toString().equals(getAnswer)) {
            option2.setBackgroundResource(R.drawable.round_back_green10);
            option2.setTextColor(Color.WHITE);
        }
        else if (option3.getText().toString().equals(getAnswer)) {
            option3.setBackgroundResource(R.drawable.round_back_green10);
            option3.setTextColor(Color.WHITE);
        }
        else if (option4.getText().toString().equals(getAnswer)) {
            option4.setBackgroundResource(R.drawable.round_back_green10);
            option4.setTextColor(Color.WHITE);
        }
    }
}
