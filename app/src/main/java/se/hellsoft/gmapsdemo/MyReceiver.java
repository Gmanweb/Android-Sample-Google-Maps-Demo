package se.hellsoft.gmapsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION_GEOFENCE_TOAST = "se.hellsoft.gmapsdemo.GEOFENCE_TOAST";
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            String action = intent.getAction();
            if(ACTION_GEOFENCE_TOAST.equals(action)) {
                Toast.makeText(context, "Your geofence was triggered!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
