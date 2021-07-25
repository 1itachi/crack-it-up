package edu.neu.madcourse.crack_it_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    TextInputLayout mEmail, mPassword, mPassword2;
    Button mSignUpButton;
    TextView loginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //get the views
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPassword2 = findViewById(R.id.password2);
        progressBar = findViewById(R.id.progressBar);
        mSignUpButton = findViewById(R.id.signup);

        fAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);
        //if user is already logged in, direct to main activity
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(Signup.this, HomeScreenActivity.class));
            finish();
        }


        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getEditText().getText().toString().trim();
                String password = mPassword.getEditText().getText().toString();
                String password2 = mPassword2.getEditText().getText().toString();


                //validations
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    mEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    mPassword.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmail.setError("Not a valid email!");
                    mEmail.requestFocus();
                    return;
                }
                if(password.length() < 6 ){
                    mPassword.setError("Password Must be greater than 6 characters");
                    mPassword.requestFocus();
                    return;
                }
                if(!password.equals(password2)){
                    mPassword2.setError("Passwords should match!");
                    mPassword2.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signup.this, "User created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Signup.this, HomeScreenActivity.class));
                        }else{
                            Toast.makeText(Signup.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }


    // called upon clicking Login button
    public void onClickSignUpButton(View view) {
        System.out.println("Sign up button clicked");
    }

    public void onClicklogin(View view) {
        System.out.println("login text clicked");


        Intent intent1 = new Intent(Signup.this, LoginActivity.class);

        startActivity(intent1);

    }


}