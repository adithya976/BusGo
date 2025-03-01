package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
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
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class AdminActivity extends AppCompatActivity {

    public Button newUsers;
    public Button bus_dataBtn,Users;
    public ImageButton message,notifications;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Users=(Button)findViewById(R.id.stu_data);
        Users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AAUser.class);
                startActivity(intent);
            }
        });

        newUsers=(Button)findViewById(R.id.feedback_btn) ;
        newUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, AANewUser.class);
                startActivity(intent);
            }
        });

        bus_dataBtn=(Button)findViewById(R.id.button6) ;
        bus_dataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, busData.class);
                startActivity(intent);
            }
        });

        message=(ImageButton) findViewById(R.id.imageButton7);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MessageInAdmin.class);
                startActivity(intent);
            }
        });

        notifications=(ImageButton) findViewById(R.id.NotificationAdminBtn);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, adminNotification.class);
                startActivity(intent);
            }
        });

        FeedbackDatabaseHelper fdh=new FeedbackDatabaseHelper(AdminActivity.this);
        Cursor cursor=fdh.ReadTheData2();
        if(cursor.getCount()!=0)
        {
            Toast.makeText(this, "Open the notification section to view the messages sent while you were away.", Toast.LENGTH_SHORT).show();
        }
    }
}