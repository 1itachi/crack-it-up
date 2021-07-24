package edu.neu.madcourse.crack_it_up;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TopicSelection extends AppCompatActivity implements RecyclerViewAdapterLearnTopicsSelection.TopicListener{

    private RecyclerView recyclerViewForAllTopics;
    private RecyclerView.LayoutManager recyclerViewLayoutManger;
    private RecyclerViewAdapterLearnTopicsSelection recyclerViewAdapter;
    private ArrayList<TopicCard> topicCards = new ArrayList<>();
    private String username, objective;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);
        username = getIntent().getStringExtra("username");
        objective = getIntent().getStringExtra("objective");
        topicCards = getTopicsFromFirebase();

        //recycler view
        recyclerViewForAllTopics = findViewById(R.id.recyclerViewAllTopics);
        recyclerViewLayoutManger = new GridLayoutManager(this, 2);
        recyclerViewForAllTopics.setLayoutManager(recyclerViewLayoutManger);

        recyclerViewAdapter = new RecyclerViewAdapterLearnTopicsSelection(topicCards, this);
        recyclerViewForAllTopics.setAdapter(recyclerViewAdapter);
    }

    private ArrayList<TopicCard> getTopicsFromFirebase() {

        ArrayList<TopicCard> topicCards = new ArrayList<>();
        topicCards.add(new TopicCard("abc"));
        topicCards.add(new TopicCard("def"));
        topicCards.add(new TopicCard("ijk"));
        topicCards.add(new TopicCard("lmn"));
        topicCards.add(new TopicCard("ijk"));
        topicCards.add(new TopicCard("lmn"));
        topicCards.add(new TopicCard("ijk"));
        topicCards.add(new TopicCard("lmn"));
        topicCards.add(new TopicCard("ijk"));
        topicCards.add(new TopicCard("lmn"));
        topicCards.add(new TopicCard("ijk"));
        topicCards.add(new TopicCard("lmn"));
        topicCards.add(new TopicCard("ijk"));
        topicCards.add(new TopicCard("lmn"));
        return  topicCards;
    }

    @Override
    public void onTopicClick(int position) {
        System.out.println("Topic clicked at position " + position);

        Intent intent;
        if(objective.equals("behavioral")) {
            intent = new Intent(this, MainActivity.class);
        }
        else {
            intent = new Intent(this, Topic.class);
        }

        intent.putExtra("TOPIC_NAME", topicCards.get(position).getTopicName());
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

    @Override
    public void onTopicButtonClick(int position) {
        System.out.println("Topic clicked at position " + position + " for objective = " + objective);

        Intent intent;
        if(objective.equals("behavioral")) {
            intent = new Intent(this, BehavioralQuestionListActivity.class);
        }
        else if (objective.equals("learn")){
            intent = new Intent(this, Topic.class);
        }
        else {
            intent = new Intent(this, HomeScreenActivity.class);
        }

        intent.putExtra("TOPIC_NAME", topicCards.get(position).getTopicName());
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
}
