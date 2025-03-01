package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class StuSendFeedback extends AppCompatActivity {
    EditText msg;
    String stu_usn,stu_name,stu_route;
    Button send;
    DatabaseHelper dh;
    FeedbackDatabaseHelper fdh;
    ImageButton delMsgSelf;
    CustomAdapterFeedback customAdapterFeedback;
    RecyclerView recyclerView;
    public ArrayList<String> Name,Usn,bus_route,message,date,time;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stu_send_feedback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dh=new DatabaseHelper(StuSendFeedback.this);
        fdh=new FeedbackDatabaseHelper(StuSendFeedback.this);
        msg=(EditText) findViewById(R.id.StuTextMultiLine2);
        send=(Button) findViewById(R.id.Send_btn_stu);
        stu_usn=getIntent().getStringExtra("usn");
        Cursor cursor=dh.Select1Record(stu_usn);
        if (cursor.moveToFirst()){
            stu_name=cursor.getString(1);
            stu_route=cursor.getString(6);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!msg.getText().toString().isEmpty()){
                    fdh.addMessage(stu_usn,stu_name,msg.getText().toString(),stu_route);
                    msg.setText("");
                    recreate();
                }else{
                    Toast.makeText(StuSendFeedback.this, "Type the message first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Name=new ArrayList<>();
        Usn=new ArrayList<>();
        bus_route=new ArrayList<>();
        message=new ArrayList<>();
        date=new ArrayList<>();
        time=new ArrayList<>();

        storeValuesInArray();
        recyclerView=(RecyclerView) findViewById(R.id.rview_StuSelf);
        customAdapterFeedback = new CustomAdapterFeedback(StuSendFeedback.this, message, Usn, Name, bus_route, date, time);
//        if(stu_usn.equals(Usn.get()))
        recyclerView.setAdapter(customAdapterFeedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(StuSendFeedback.this));


        delMsgSelf=(ImageButton) findViewById(R.id.del_self_btn_stu);
        delMsgSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(StuSendFeedback.this);
                builder.setTitle("Delete all the messages for You?");
                builder.setMessage("Are you sure you want to delete all the messages?\n\nNOTE:The messages are only deleted on your device. The receiver has already received the message and that cannot be deleted.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fdh.ClearStuMsg(stu_usn);
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
        Cursor cursor=fdh.ReadMsg2(stu_usn);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No Messages Sent", Toast.LENGTH_SHORT).show();
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

}