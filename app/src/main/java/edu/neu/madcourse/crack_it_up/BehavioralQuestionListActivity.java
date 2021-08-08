package edu.neu.madcourse.crack_it_up;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BehavioralQuestionListActivity extends AppCompatActivity implements RecyclerViewAdapterQuestionsForTopic.QuestionListener{
    private String username, topicName;

    private RecyclerView recyclerViewForAllQuestions;
    private RecyclerView.LayoutManager recyclerViewLayoutManger;
    private RecyclerViewAdapterQuestionsForTopic recyclerViewAdapter;
    private ArrayList<QuestionCard> questionsCards = new ArrayList<>();
    private DatabaseReference mDatabase;
    private DatabaseReference mQuestion;
    private List<String> userAnsweredQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral_question_list);
        getUserAnsweredQuestions();

        username = getIntent().getStringExtra("username");
        topicName = getIntent().getStringExtra("TOPIC_NAME");

        TextView topicTextView = findViewById(R.id.topicTextView);
        topicTextView.setText(topicName);

        getQuestionsFromFirebase();

        //recycler view
        recyclerViewForAllQuestions = findViewById(R.id.recyclerViewAllQuestions);
        recyclerViewLayoutManger = new LinearLayoutManager(this);
        recyclerViewForAllQuestions.setLayoutManager(recyclerViewLayoutManger);

        recyclerViewAdapter = new RecyclerViewAdapterQuestionsForTopic(questionsCards, userAnsweredQuestions, this);
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

    private void getQuestionsFromFirebase() {
        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //get topic id
        String topicId = getIntent().getStringExtra("TOPIC_ID");
        //get question reference
        mQuestion = mDatabase.child("behavioralQuestion").child(topicId);

        mQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionsCards.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    QuestionCard card = snapshot1.getValue(QuestionCard.class);
                    questionsCards.add(card);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    @Override
    public void onQuestionClick(int position) {
        QuestionCard questionCard = questionsCards.get(position);
        System.out.println("Clicked question at position " + questionCard.getQuestionId() + " and " +
                "question is: " +questionCard.getQuestionText());

        //Intent intent = new Intent(this, BehavioralQuestionRecordActivity.class);
        Intent intent = new Intent(this, BehavioralAudioRecordActivity.class);
        intent.putExtra("QUESTION_ID", questionCard.getQuestionId());
        intent.putExtra("QUESTION_TEXT", questionCard.getQuestionText());
        intent.putExtra("ANSWER", questionCard.getIdealAnswer());
        intent.putExtra("TOPIC_NAME", topicName);
        intent.putExtra("USERNAME", username);
        if(userAnsweredQuestions.contains(questionCard.getQuestionId())){
            intent.putExtra("ATTEMPTED_BY_USER", "true");
        } else {
            intent.putExtra("ATTEMPTED_BY_USER", "false");
        }
        startActivity(intent);
    }

    private void getUserAnsweredQuestions() {
        String audioFilePath = getExternalFilesDir("/").getAbsolutePath();
        File audioDirectory = new File(audioFilePath);
        userAnsweredQuestions = new ArrayList<>();
        for(File file : audioDirectory.listFiles()){
            userAnsweredQuestions.add(file.getName());
        }
    }
}