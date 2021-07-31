package edu.neu.madcourse.crack_it_up;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;

public class RecyclerViewAdapterAudioHistory extends RecyclerView.Adapter<RecyclerViewAdapterAudioHistory.RecyclerViewHolder>{

    private File[] audioRecordingList;
    private RecordingListener recordingListener;

    public RecyclerViewAdapterAudioHistory(File[] audioRecordingList, RecordingListener recordingListener) {
        this.audioRecordingList = audioRecordingList;
        this.recordingListener = recordingListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.audio_recording_row, parent, false);
        return new RecyclerViewHolder(view, recordingListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        File audioFile = audioRecordingList[position];
        holder.audioTitleTextView.setText(audioRecordingList[position].getName());
        holder.audioDateTextView.setText(audioRecordingList[position].lastModified() + "");
    }

    @Override
    public int getItemCount() {
        return audioRecordingList.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView audioTitleTextView, audioDateTextView;
        private ImageView playImageButton;
        private RecordingListener recordingListener;

        public RecyclerViewHolder(@NonNull View itemView, RecordingListener recordingListener) {
            super(itemView);
            audioTitleTextView = itemView.findViewById(R.id.audioTitleTextView);
            audioDateTextView = itemView.findViewById(R.id.audioDateTextView);
            playImageButton = itemView.findViewById(R.id.audioRowPlayImageView);

            this.recordingListener = recordingListener;
            itemView.setOnClickListener(this);
            playImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordingListener.onPlayButtonClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            recordingListener.onAudioClick(getAdapterPosition());
        }
    }

    public interface RecordingListener{
        void onAudioClick(int position);

        void onPlayButtonClick(int position);
    }
}
