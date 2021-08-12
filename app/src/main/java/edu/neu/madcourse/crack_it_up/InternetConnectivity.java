package edu.neu.madcourse.crack_it_up;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.sql.SQLOutput;

public class InternetConnectivity extends BroadcastReceiver {
    Dialog dialog;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        boolean status = NetworkUtil.checkInternet(context);
        dialog = new Dialog(context, R.style.Theme_MaterialComponents_DayNight_NoActionBar);

        dialog.setContentView(R.layout.no_internet);
        Button retry = dialog.findViewById(R.id.button);
        retry.setOnClickListener(v->{
            dialog.dismiss();
            ((Activity) context).finish();
            Intent intent1 = new Intent(context, HomeScreenActivity.class);
            context.startActivity(intent1);
        });

        if(!status){
            try {
                dialog.show();
                Toast.makeText(context, "No Internet!", Toast.LENGTH_SHORT).show();
            }
            catch (WindowManager.BadTokenException e) {
                //use a log message
                System.out.println("Error");
            }

        }
    }

    public Dialog dialogInstance(){
        return dialog;
    }
}