package com.example.project3app2cs478;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;


//creating a class to listen for intents/broadcasts from other apps
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent activityIntent;
        if ("ATTRACTIONS".equals(intent.getAction())) {
            activityIntent = new Intent(context, AttractionActivity.class);
        } else if ("RESTAURANTS".equals(intent.getAction())) {
            activityIntent = new Intent(context, RestaurantsActivity.class);
        } else {
            return;
        }

        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required for receiver
        context.startActivity(activityIntent);
    }
}