package edu.neu.madcourse.crack_it_up;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterQuestionsForTopic extends RecyclerView.Adapter<RecyclerViewAdapterQuestionsForTopic.RecyclerViewHolder>{

    private ArrayList<QuestionCard> questionCardList;
    private QuestionListener questionListener;

    public RecyclerViewAdapterQuestionsForTopic(ArrayList<QuestionCard> questionCardList, QuestionListener questionListener) {
        this.questionCardList = questionCardList;
        this.questionListener = questionListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.behavioral_question_row, parent, false);
        return new RecyclerViewHolder(view, questionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String questionText = questionCardList.get(position).getQuestionText();
        holder.questionTextView.setText(questionText);
        holder.historicResponseButton.setTag(questionCardList.get(position).getQuestionId());
    }

    @Override
    public int getItemCount() {
        return questionCardList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView questionTextView;
        private ImageView historicResponseButton;
        private QuestionListener questionListener;

        public RecyclerViewHolder(@NonNull View itemView, QuestionListener questionListener) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            historicResponseButton = itemView.findViewById(R.id.historicResponseButton);
            this.questionListener = questionListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            questionListener.onQuestionClick(getAdapterPosition());
        }
    }

    public interface QuestionListener{
        void onQuestionClick(int position);
    }
}
