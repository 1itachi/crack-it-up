package edu.neu.madcourse.crack_it_up;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class RecyclerViewAdapterUserProgress extends RecyclerView.Adapter<RecyclerViewAdapterUserProgress.RecyclerViewHolder>{

    List<UserScore>  userScores;

    public RecyclerViewAdapterUserProgress(List<UserScore> userScores) {
        this.userScores = userScores;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_progress_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.topicTextView.setText("" + userScores.get(position).getTopicName());
        if(userScores.get(position).getScore() == -99) {
            holder.scoreTextView.setText("NA");
            holder.assignmentImage.setImageResource(R.drawable.ic_baseline_assignment);
        } else {
            holder.scoreTextView.setText("" + userScores.get(position).getScore() + "%");
            if(userScores.get(position).getScore() < 70) {
                holder.assignmentImage.setImageResource(R.drawable.ic_assignmnet_below_threshold);
            }
        }

    }

    @Override
    public int getItemCount() {
        return userScores.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView topicTextView, scoreTextView;
        private ImageView assignmentImage;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            topicTextView = itemView.findViewById(R.id.topicNameProgressTextView);
            scoreTextView = itemView.findViewById(R.id.percentCompletionProgressTextView);
            assignmentImage = itemView.findViewById(R.id.assignmentCompletionImageView);
        }
    }

}
