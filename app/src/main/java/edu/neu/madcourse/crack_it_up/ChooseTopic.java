package edu.neu.madcourse.crack_it_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ChooseTopic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_topic);
        CardView phy = findViewById(R.id.topic1_card);
        // Set a click listener on that View
        phy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topic1Intent = new Intent(ChooseTopic.this, Topic.class);
                startActivity(topic1Intent);
            }
        });

        CardView chem = findViewById(R.id.topic2_card);
        // Set a click listener on that View
        chem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topic2Intent = new Intent(ChooseTopic.this, Topic.class);
                startActivity(topic2Intent);
            }
        });

        CardView bio = findViewById(R.id.topic3_card);
        // Set a click listener on that View
        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topic3Intent = new Intent(ChooseTopic.this, Topic.class);
                startActivity(topic3Intent);
            }
        });

        CardView more = findViewById(R.id.more_card);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "More topics will be added soon";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
}