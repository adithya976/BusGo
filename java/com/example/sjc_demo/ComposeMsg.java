package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class ComposeMsg extends AppCompatActivity {

    TextView messageHolder;
    Button sendBtn;
    AdminMsgDatabaseHelper amdh;
    public ArrayList<String> Message,Date,Time;
    CustomAdapterMessageAdmin customAdapterMessageAdmin;
    ImageButton delMsgSelf;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compose_msg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        amdh=new AdminMsgDatabaseHelper(ComposeMsg.this);
        messageHolder=(TextView) findViewById(R.id.AdminMsgHolder);
        sendBtn=(Button) findViewById(R.id.Send_button);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!messageHolder.getText().toString().isEmpty()){
                    amdh.AddMessageAdmin(messageHolder.getText().toString());
                    messageHolder.setText("");
                    recreate();
                }else{
                    Toast.makeText(ComposeMsg.this, "Type the message first!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Message=new ArrayList<>();
        Date=new ArrayList<>();
        Time=new ArrayList<>();
        StoreMsgInArray();
        recyclerView=(RecyclerView) findViewById(R.id.rview_adminSelf);

        customAdapterMessageAdmin = new CustomAdapterMessageAdmin(ComposeMsg.this, Message,Date,Time);
        recyclerView.setAdapter(customAdapterMessageAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(ComposeMsg.this));

        delMsgSelf=(ImageButton) findViewById(R.id.del_self_btn);
        delMsgSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ComposeMsg.this);
                builder.setTitle("Delete all the messages for You?");
                builder.setMessage("Are you sure you want to delete all the messages?\n\nNOTE:The messages are only deleted on your device. The receiver has already received the message and that cannot be deleted.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        amdh.ClearAdminMsg();
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
        Cursor cursor= amdh.ReadMsg2();
        if(cursor.getCount()==0)
        {
            return;
        }
        else {
            while(cursor.moveToNext()){
                Message.add(cursor.getString(1));
                Date.add(cursor.getString(2));
                Time.add(cursor.getString(3));
            }
        }
        Collections.reverse(Message);
        Collections.reverse(Date);
        Collections.reverse(Time);
    }
}