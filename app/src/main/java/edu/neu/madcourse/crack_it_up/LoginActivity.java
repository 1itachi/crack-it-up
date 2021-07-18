package edu.neu.madcourse.crack_it_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // called upon clicking Login button
    public void onClickLoginButton(View view) {
        System.out.println("Login button clicked");


        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);

        startActivity(intent1);


        //logic to handle login activity (should be implemented later)
//        TextView usernameTextView = (TextView) findViewById(R.id.editUsername);
//        String username = usernameTextView.getText().toString().replaceAll("\\s", "");
//
//        if (username.length() == 0) {
//            Toast myToast = Toast.makeText(this, "Enter Username!", Toast.LENGTH_LONG);
//            myToast.show();
//        }else {
//            Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
//            intent1.putExtra("USERNAME", username);
//            startActivity(intent1);
//        }
    }


}