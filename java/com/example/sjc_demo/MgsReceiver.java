package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class MgsReceiver extends BroadcastReceiver {
    private static final String TAG = "ToastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Alarm triggered!");
        String name="BUS SERVICE/MAINTENANCE REMINDER";
        String route="";
        String usn="";
        String message="This is a quarterly reminder to send the buses for servicing.\nProper maintenance of the buses enhances the overall life of the bus.";
        FeedbackDatabaseHelper fdh=new FeedbackDatabaseHelper(context);
        fdh.addMessage(usn,name,message,route);

//        Intent serviceIntent = new Intent(context, InboxMsg.class);
//        serviceIntent.putExtra("triggered", true);
//        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this flag
//        context.startActivity(serviceIntent);
//        scheduleNextAlarm(context);
    }

//    @SuppressLint("ScheduleExactAlarm")
//    private void scheduleNextAlarm(Context context) {
//        Intent intent = new Intent(context, MgsReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        long interval = 10 * 1000; // Interval set to 10 seconds
//        long triggerAtMillis = System.currentTimeMillis() + interval;
//        if (alarmManager != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//            } else {
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//            }
//        }
//    }
}
