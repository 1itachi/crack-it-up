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

    File[] audioRecordingList;
    private RecordingListener recordingListener;
    private TimeCalculator timeCalculator;

    public RecyclerViewAdapterAudioHistory(File[] audioRecordingList, RecordingListener recordingListener) {
        this.audioRecordingList = audioRecordingList;
        this.recordingListener = recordingListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        timeCalculator = new TimeCalculator();
        View view = layoutInflater.inflate(R.layout.audio_recording_row, parent, false);
        return new RecyclerViewHolder(view, recordingListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.audioFile = audioRecordingList[position];
        holder.audioTitleTextView.setText(audioRecordingList[position].getName());
        holder.audioDateTextView.setText(timeCalculator.getTimeDifference(audioRecordingList[position].lastModified()) + "");
    }

    @Override
    public int getItemCount() {
        return audioRecordingList.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView audioTitleTextView, audioDateTextView;
        private ImageView playImageButton;
        private RecordingListener recordingListener;
        private File audioFile;

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
                    recordingListener.onAudioClick(audioFile, getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            recordingListener.onAudioClick(audioFile, getAdapterPosition());
        }
    }

    public interface RecordingListener{
        void onAudioClick(File file, int position);
    }
}
