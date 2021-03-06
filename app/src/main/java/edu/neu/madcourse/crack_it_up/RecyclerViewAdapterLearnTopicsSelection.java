package edu.neu.madcourse.crack_it_up;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapterLearnTopicsSelection extends RecyclerView.Adapter<RecyclerViewAdapterLearnTopicsSelection.RecyclerViewHolder>{

    private ArrayList<TopicCard> topic_card_list;
    private TopicListener topicListener;

    public RecyclerViewAdapterLearnTopicsSelection(ArrayList<TopicCard> topic_card_list, TopicListener topicListener) {
        this.topic_card_list = topic_card_list;
        this.topicListener = topicListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.topic_row, parent, false);
        return new RecyclerViewHolder(view, topicListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String topicName = topic_card_list.get(position).getName();
        holder.topicButton.setText(topicName);
    }

    @Override
    public int getItemCount() {
        return topic_card_list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Button topicButton;
        private TopicListener topicListener;

        public RecyclerViewHolder(@NonNull View itemView, TopicListener topicListener) {
            super(itemView);
            topicButton = itemView.findViewById(R.id.topicSelectButton);
            this.topicListener = topicListener;
            itemView.setOnClickListener(this);

            topicButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    topicListener.onTopicButtonClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            topicListener.onTopicClick(getAdapterPosition());
        }
    }

    public interface TopicListener{
        void onTopicClick(int position);

        void onTopicButtonClick(int position);
    }
}
