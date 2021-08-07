package edu.neu.madcourse.crack_it_up;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TakeQuizDialog extends AppCompatDialogFragment {
    Context context;

    public TakeQuizDialog(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        Bundle args = getArguments();
        String topicId, topicName;
        assert args != null;
        topicId = args.getString("topicId");
        topicName = args.getString("topicName");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Take Quiz")
                .setMessage("Do you wish to take the quiz to test your knowledge?")
                .setPositiveButton("Yes, sure!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, QuizActivity.class);
                        intent.putExtra("TOPIC_ID", topicId);
                        intent.putExtra("TOPIC_NAME", topicName);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, HomeScreenActivity.class);
                        intent.putExtra("TOPIC_ID", topicId);
                        intent.putExtra("TOPIC_NAME", topicName);
                        startActivity(intent);
                    }
                });;
        return builder.create();
    }

}
