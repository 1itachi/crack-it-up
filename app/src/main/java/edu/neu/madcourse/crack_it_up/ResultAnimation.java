package edu.neu.madcourse.crack_it_up;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

public class ResultAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_animation);

        View view = findViewById(R.id.animation);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) view;


        final AppCompatButton startNewBtn = findViewById(R.id.startNewQuizBtn);
        final TextView correctAnswer = findViewById(R.id.correctAnswers);
        final TextView userMessage = findViewById(R.id.userMessage);

        final int getCorrectAnswers = getIntent().getIntExtra("percentage", 0);
        final String topicId = getIntent().getStringExtra("topicId");

        if(getCorrectAnswers >= 70){
            lottieAnimationView.setAnimation(R.raw.congrats);
            userMessage.setText("Well done!");
        }else{
            lottieAnimationView.setAnimation(R.raw.sad);
            userMessage.setText("Practice again and retry the quiz");
        }

        correctAnswer.setText("You scored " +String.valueOf(getCorrectAnswers) + "%");

        //get current user
        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();
        String email = user.getEmail();

        String formattedEmail = email.replace("@", "").replace(".", "");


        //firebase
        //reference to firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("quizScores");
        //set user reference
        mDatabase.child(formattedEmail).child(topicId).setValue(getCorrectAnswers);


        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultAnimation.this, TopicSelection.class);
                intent.putExtra("objective", "quiz");
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResultAnimation.this, HomeScreenActivity.class));
        finish();
    }

}