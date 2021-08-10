package edu.neu.madcourse.crack_it_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CoverLetterTips extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_letter_tips);

        username = getIntent().getStringExtra("username");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.homePage);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.homePage:
                    intent = new Intent(this, HomeScreenActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case R.id.profilePage:
                    intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

    }
}