package edu.neu.madcourse.crack_it_up;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class BehavioralExampleAnswerDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        Bundle args = getArguments();
        String exampleAnswer;
        if (args == null) {
            exampleAnswer = "No answer is available yet";
        } else {
            exampleAnswer = args.getString("answer");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Example answer")
                .setMessage(exampleAnswer)
                .setPositiveButton("Helpful", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

}
