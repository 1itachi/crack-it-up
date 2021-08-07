package edu.neu.madcourse.crack_it_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuizResults extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        final AppCompatButton startNewBtn = findViewById(R.id.startNewQuizBtn);
        final TextView correctAnswer = findViewById(R.id.correctAnswers);

        final int getCorrectAnswers = getIntent().getIntExtra("percentage", 0);
        final String topicId = getIntent().getStringExtra("topicId");

        correctAnswer.setText("You scored " +String.valueOf(getCorrectAnswers) + "%");

        //get current user
        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();
        String email = user.getEmail();

        String formattedEmail = email.replace("@", "").replace(".", "");


        //firebase
        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("quizScores");
        //set user reference
        mDatabase.child(formattedEmail).child(topicId).setValue(getCorrectAnswers);


        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizResults.this, QuizActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuizResults.this, MainActivity.class));
        finish();
    }
}
