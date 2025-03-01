package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class Stu_inbox extends AppCompatActivity {
    public ArrayList<String> Message,date,time;
    AdminMsgDatabaseHelper amdh;
    CustomAdapterMessageAdmin customAdapterMessageAdmin;

    RecyclerView recyclerView;
    ImageButton deleteAll;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stu_inbox);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Message=new ArrayList<>();
        date=new ArrayList<>();
        time=new ArrayList<>();
        amdh=new AdminMsgDatabaseHelper(Stu_inbox.this);
        deleteAll=(ImageButton) findViewById(R.id.del_msgadmin_btn);
        StoreMsgInArray();
        recyclerView=(RecyclerView) findViewById(R.id.inb_Stu_recycler_view);

        customAdapterMessageAdmin = new CustomAdapterMessageAdmin(Stu_inbox.this, Message,date,time);
        recyclerView.setAdapter(customAdapterMessageAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(Stu_inbox.this));

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Stu_inbox.this);
                builder.setTitle("Clear the Inbox");
                builder.setMessage("Are you sure you want to delete all the messages?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        amdh.DeleteAll();
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

    void StoreMsgInArray(){
        Cursor cursor= amdh.ReadMsg();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No Messages", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                Message.add(cursor.getString(1));
                date.add(cursor.getString(2));
                time.add(cursor.getString(3));
            }
        }
        Collections.reverse(Message);
        Collections.reverse(date);
        Collections.reverse(time);
    }
}