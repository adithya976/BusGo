package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class InboxMsg extends AppCompatActivity {

    public ArrayList<String> Name,Usn,bus_route,message,date,time;
    CustomAdapterFeedback customAdapterFeedback;
    FeedbackDatabaseHelper fdh;
    RecyclerView recyclerView;
    ImageButton deleteAll;
    Button reminder;
    private static final int REQUEST_CODE_PERMISSION = 1001;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inbox_msg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        deleteAll=(ImageButton) findViewById(R.id.del_allmsg_btn) ;
        fdh=new FeedbackDatabaseHelper(InboxMsg.this);
        Name=new ArrayList<>();
        Usn=new ArrayList<>();
        bus_route=new ArrayList<>();
        message=new ArrayList<>();
        date=new ArrayList<>();
        time=new ArrayList<>();

        storeValuesInArray();
        recyclerView=(RecyclerView) findViewById(R.id.inb_Admin_recycler_view);

        customAdapterFeedback = new CustomAdapterFeedback(InboxMsg.this, message, Usn, Name, bus_route, date, time);
        recyclerView.setAdapter(customAdapterFeedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(InboxMsg.this));
        scheduleMessage();
//        if (getIntent().getBooleanExtra("triggered", false)) {
//            addMsg();
//        }

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(InboxMsg.this);
                builder.setTitle("Clear the Inbox");
                builder.setMessage("Are you sure you want to delete all the messages?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fdh.DeleteAll();
                        recreate();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        reminder=(Button) findViewById(R.id.reminder);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMsg();
            }
        });

    }

    void storeValuesInArray(){
        Cursor cursor=fdh.ReadTheData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No Messages", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                Usn.add(cursor.getString(1));
                Name.add(cursor.getString(2));
                message.add(cursor.getString(3));
                bus_route.add(cursor.getString(4));
                date.add(cursor.getString(5));
                time.add(cursor.getString(6));
            }
        }
        Collections.reverse(Usn);
        Collections.reverse(Name);
        Collections.reverse(message);
        Collections.reverse(bus_route);
        Collections.reverse(date);
        Collections.reverse(time);
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleMessage(){
        Intent intent=new Intent(this,MgsReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval=3*10*1000;
        long triggerAtMillis=System.currentTimeMillis()+interval;
        if(alarmManager!=null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,triggerAtMillis,interval,pendingIntent);
        }
    }

    public void addMsg(){
        String name="BUS SERVICE/MAINTENANCE REMINDER";
        String route="";
        String usn="";
        String message="This is a quarterly reminder to send the buses for servicing.\nProper maintenance of the buses enhances the overall life of the bus.";
        FeedbackDatabaseHelper fdh=new FeedbackDatabaseHelper(InboxMsg.this);
        fdh.addMessage(usn,name,message,route);
        recreate();
    }

}