package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class StudentActivity extends AppCompatActivity {

    public Button busInfo,feedback,inbox;
    public ImageButton notification,emergency;
    String route,usn,name;
    FeedbackDatabaseHelper fdh;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fdh=new FeedbackDatabaseHelper(StudentActivity.this);
        route=getIntent().getStringExtra("route");
        usn=getIntent().getStringExtra("usn");
        name=getIntent().getStringExtra("name");
        busInfo=(Button) findViewById(R.id.bus_info_stu);
        feedback=(Button) findViewById(R.id.feedback_btn);
        inbox=(Button) findViewById(R.id.inbox_stu);
        emergency=(ImageButton) findViewById(R.id.EmergencyBtn);
        notification=(ImageButton) findViewById(R.id.NotificationBtn);
        busInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this,MyBusInfoStu.class);
                intent.putExtra("route",route);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this,StuSendFeedback.class);
                intent.putExtra("usn",usn);
                startActivity(intent);
            }
        });

        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this,Stu_inbox.class);
                startActivity(intent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
                builder.setTitle("Emergency Message");
                builder.setMessage("Is there any emergency?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String emer="❗⚠\uFE0F❗ EMERGENCY ❗⚠\uFE0F❗";
                        fdh.addMessage(usn,name,emer,route);
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

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, StuViewNotifications.class);
                startActivity(intent);
            }
        });
    }
}