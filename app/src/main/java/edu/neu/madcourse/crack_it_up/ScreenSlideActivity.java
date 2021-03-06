package edu.neu.madcourse.crack_it_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScreenSlideActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;

    private  TextView[] mDots;
    private Button mNextBtn;
    private Button mBackBtn;

    private int mCurrentPage;

    private DatabaseReference mDatabase;
    private DatabaseReference mCard;

    private String topicId, topicName;
    private ArrayList<FlashCard> listOfFlashcards = new ArrayList<>();
    private Context context;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                //At this point you should start the login activity and finish this one
                finish();
            }

        }, intentFilter);
        context = this;
        setContentView(R.layout.activity_screen_slide);
        broadcastReceiver = new InternetConnectivity();
        checkInternet();
        mSlideViewPager = (ViewPager) findViewById(R.id.pager);

        mNextBtn = (Button) findViewById(R.id.next);
        mBackBtn = (Button) findViewById(R.id.previous);


        //set the adapter
        sliderAdapter = new SliderAdapter(this, listOfFlashcards);
        mSlideViewPager.setAdapter(sliderAdapter);

        mSlideViewPager.addOnPageChangeListener(viewListener);


        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //get topic id
        topicId = getIntent().getStringExtra("TOPIC_ID");
        topicName = getIntent().getStringExtra("TOPIC_NAME");
        //get user reference
        mCard = mDatabase.child("flashcard").child(topicId);


        mCard.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOfFlashcards.clear();

                for(DataSnapshot snapshot1 :snapshot.getChildren()){
                    FlashCard card = snapshot1.getValue(FlashCard.class);
                    listOfFlashcards.add(card);
                }

                //sort based on page number
                listOfFlashcards.sort((c1,c2)->c1.getPageNumber() - c2.getPageNumber());

                sliderAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //OnCLickListeners
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if(b.getText().toString().equals("Finish")){
                    openDialogQuiz();
//                    startActivity(new Intent(ScreenSlideActivity.this, HomeScreenActivity.class));
                }
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

    }

    private void checkInternet() {
        registerReceiver(broadcastReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void openDialogQuiz() {
        Bundle args = new Bundle();
        args.putString("topicId", topicId);
        args.putString("topicName", topicName);

        TakeQuizDialog dialog = new TakeQuizDialog(context);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "take quiz");
    }



    @Override
    protected void onStart() {
        super.onStart();
        checkInternet();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternet();
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPage = position;
            if(position == 0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText("Next");
                mBackBtn.setText("");
            }else if (position == listOfFlashcards.size()-1){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");
            }else{
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}