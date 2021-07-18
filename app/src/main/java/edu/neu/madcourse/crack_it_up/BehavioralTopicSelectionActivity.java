package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BehavioralTopicSelectionActivity extends AppCompatActivity {

    private String username;

    private RecyclerView recyclerViewForAllBehavioralTopics;
    private RecyclerView.LayoutManager recyclerViewLayoutManger;
    private RecyclerViewAdapterBehavioralTopics recyclerViewAdapterBehavioralTopics;
    private ArrayList<BehavioralTopicCard> behavioralTopicCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral_topic_selection);

        behavioralTopicCards = getAllTopicsFromFirebase();
        username = "alex"; //temporary

        TextView test = findViewById(R.id.textView);
        System.out.println(test.getText());
        recyclerViewForAllBehavioralTopics = findViewById(R.id.behavioralTopicRecyclerView);
        recyclerViewLayoutManger = new GridLayoutManager(this, 2);
        recyclerViewForAllBehavioralTopics.setLayoutManager(recyclerViewLayoutManger);

        recyclerViewAdapterBehavioralTopics = new RecyclerViewAdapterBehavioralTopics(behavioralTopicCards, username);
        recyclerViewForAllBehavioralTopics.setAdapter(recyclerViewAdapterBehavioralTopics);
    }

    private ArrayList<BehavioralTopicCard> getAllTopicsFromFirebase() {
        ArrayList<BehavioralTopicCard> cards = new ArrayList<>();
        cards.add(new BehavioralTopicCard("Arrays"));
        cards.add(new BehavioralTopicCard("Linked List"));
        cards.add(new BehavioralTopicCard("DP"));
        return cards;
    }

    public void onClickTopicButton(int position) {
        System.out.println("CLicked topic button at position " + position);

        Intent intent = new Intent(this, HomeScreenActivity.class); //replace HomeScreenActivity with new screen
        intent.putExtra("topic_name", behavioralTopicCards.get(position).getTopicName());
        intent.putExtra("username", username);

        startActivity(intent);
    }


}