package edu.neu.madcourse.crack_it_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    // called upon clicking Login button
    public void onClickSignUpButton(View view) {
        System.out.println("Sign up button clicked");


        Intent intent1 = new Intent(Signup.this, HomeScreenActivity.class);

        startActivity(intent1);

    }

    public void onClicklogin(View view) {
        System.out.println("login text clicked");


        Intent intent1 = new Intent(Signup.this, LoginActivity.class);

        startActivity(intent1);

    }


}