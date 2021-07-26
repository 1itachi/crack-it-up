package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class BehavioralQuestionListActivity extends AppCompatActivity implements RecyclerViewAdapterQuestionsForTopic.QuestionListener{
    private String username, topicName;

    private RecyclerView recyclerViewForAllQuestions;
    private RecyclerView.LayoutManager recyclerViewLayoutManger;
    private RecyclerViewAdapterQuestionsForTopic recyclerViewAdapter;
    private ArrayList<QuestionCard> questionsCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral_question_list);

        username = getIntent().getStringExtra("username");
        topicName = getIntent().getStringExtra("TOPIC_NAME");

        TextView topicTextView = findViewById(R.id.topicTextView);
        topicTextView.setText(topicName);

        questionsCards = getQuestionsFromFirebase();

        //recycler view
        recyclerViewForAllQuestions = findViewById(R.id.recyclerViewAllQuestions);
        recyclerViewLayoutManger = new LinearLayoutManager(this);
        recyclerViewForAllQuestions.setLayoutManager(recyclerViewLayoutManger);

        recyclerViewAdapter = new RecyclerViewAdapterQuestionsForTopic(questionsCards, this);
        recyclerViewForAllQuestions.setAdapter(recyclerViewAdapter);

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

    private ArrayList<QuestionCard> getQuestionsFromFirebase() {
        ArrayList<QuestionCard> questionCards = new ArrayList<>();

        for (int i=1; i <= 20; i++) {
            questionCards.add(new QuestionCard(i, "question example test abc def ijk lmn pqr"+i));
        }
        return questionCards;
    }

    @Override
    public void onQuestionClick(int position) {
        QuestionCard questionCard = questionsCards.get(position);
        System.out.println("Clicked question at position " + questionCard.getQuestionId() + " and " +
                "question is: " +questionCard.getQuestionText());

        Intent intent = new Intent(this, BehavioralQuestionRecordActivity.class);
        intent.putExtra("QUESTION_ID", questionCard.getQuestionId());
        intent.putExtra("QUESTION_TEXT", questionCard.getQuestionText());
        intent.putExtra("TOPIC_NAME", topicName);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

    @Override
    public void onHistoricResponseButtonClick(int position) {
        int questionId = questionsCards.get(position).getQuestionId();
        System.out.println("Clicked history button for question id " + questionId);
    }
}