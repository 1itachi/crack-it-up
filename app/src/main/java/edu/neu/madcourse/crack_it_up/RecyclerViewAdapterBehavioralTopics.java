package edu.neu.madcourse.crack_it_up;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterBehavioralTopics extends RecyclerView.Adapter<RecyclerViewAdapterBehavioralTopics.RecyclerViewHolder> {
    private ArrayList<BehavioralTopicCard> behavioralTopicCards;
    private String username;

    public RecyclerViewAdapterBehavioralTopics(ArrayList<BehavioralTopicCard> behavioralTopicCards, String username) {
        this.behavioralTopicCards = behavioralTopicCards;
        this.username = username;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterBehavioralTopics.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.behavioral_topc_cardview, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterBehavioralTopics.RecyclerViewHolder holder, int position) {
        BehavioralTopicCard behavioralTopicCard = behavioralTopicCards.get(position);
        holder.behavioralTopicButton.setText(behavioralTopicCard.getTopicName());
    }

    @Override
    public int getItemCount() {
        return behavioralTopicCards.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private Button behavioralTopicButton;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            behavioralTopicButton = itemView.findViewById(R.id.behavioralTopicButton);
        }
    }
}
