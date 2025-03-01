package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class adminNotification extends AppCompatActivity {


    public ArrayList<String> Name,Usn,bus_route,message,date,time,id;
    CustomAdapterNotifyInAdmin customAdapterNotifyInAdmin;
    FeedbackDatabaseHelper fdh;
    RecyclerView recyclerView;
    ImageButton deleteAll;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fdh=new FeedbackDatabaseHelper(adminNotification.this);
        Name=new ArrayList<>();
        Usn=new ArrayList<>();
        bus_route=new ArrayList<>();
        message=new ArrayList<>();
        date=new ArrayList<>();
        time=new ArrayList<>();
        id=new ArrayList<>();

        storeValuesInArray();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerNotifAdmin);

        customAdapterNotifyInAdmin = new CustomAdapterNotifyInAdmin(adminNotification.this, date, time, Name, Usn, message, id);
        recyclerView.setAdapter(customAdapterNotifyInAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(adminNotification.this));

        deleteAll=(ImageButton) findViewById(R.id.del_notif_btn);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(adminNotification.this);
                builder.setTitle("Clear all the Notifications ?");
                builder.setMessage("Are you sure you want to clear all the notifications?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FeedbackDatabaseHelper fdh=new FeedbackDatabaseHelper(adminNotification.this);
                        fdh.onSeen2();
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

    }

    void storeValuesInArray(){
        Cursor cursor=fdh.ReadTheData2();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No Recent Messages", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                Usn.add(cursor.getString(1));
                Name.add(cursor.getString(2));
                message.add(cursor.getString(3));
                bus_route.add(cursor.getString(4));
                date.add(cursor.getString(5));
                time.add(cursor.getString(6));
            }
        }
        Collections.reverse(id);
        Collections.reverse(Usn);
        Collections.reverse(Name);
        Collections.reverse(message);
        Collections.reverse(bus_route);
        Collections.reverse(date);
        Collections.reverse(time);
    }
}