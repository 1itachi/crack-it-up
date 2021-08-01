package edu.neu.madcourse.crack_it_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    TextInputLayout mEmail, mPassword;
    Button mLoginButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //get the views
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        mLoginButton = findViewById(R.id.login);

        progressBar.setVisibility(View.INVISIBLE);

        fAuth = FirebaseAuth.getInstance();
        //if user is already logged in, direct to main activity
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
            finish();
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getEditText().getText().toString().trim();
                String password = mPassword.getEditText().getText().toString();

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


                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Login failed! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
            });
    }



    // called upon clicking Login button
    public void onClickSignUp(View view) {
        System.out.println("Sign up text clicked");


        Intent intent1 = new Intent(LoginActivity.this, Signup.class);

        startActivity(intent1);

    }


}