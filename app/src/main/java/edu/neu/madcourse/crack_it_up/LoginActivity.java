package edu.neu.madcourse.crack_it_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        //if user is already logged in, direct to main activity
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
            finish();
        }
    }

    // called upon clicking Login button
    public void onClickLoginButton(View view) {
        System.out.println("Login button clicked");


//        TextView usernameTextView = (TextView) findViewById(R.id.userName);
//        String username = usernameTextView.getText().toString().replaceAll("\\s", "");

        TextInputLayout textInputLayout = findViewById(R.id.userName);
        String username = textInputLayout.getEditText().getText().toString().replaceAll("\\s", "");
        ;


        if (username.length() == 0) {
            Toast myToast = Toast.makeText(this, "Enter Username!", Toast.LENGTH_LONG);
            myToast.show();
        }else {
            Intent intent1 = new Intent(LoginActivity.this, HomeScreenActivity.class);
            intent1.putExtra("USERNAME", username);
            startActivity(intent1);
        }

    }

    // called upon clicking Login button
    public void onClickSignUp(View view) {
        System.out.println("Sign up text clicked");


        Intent intent1 = new Intent(LoginActivity.this, Signup.class);

        startActivity(intent1);

    }


}