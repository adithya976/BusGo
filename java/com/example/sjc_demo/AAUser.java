package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class AAUser extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton DelAllStuData;
    DatabaseHelper dh;
    public ArrayList<String> usn,name,route,phone,dob,address,fee,pass;
    CustomAdapter customAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aauser);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dh=new DatabaseHelper(AAUser.this);
        usn=new ArrayList<>();
        name=new ArrayList<>();
        route=new ArrayList<>();
        dob=new ArrayList<>();
        address=new ArrayList<>();
        fee=new ArrayList<>();
        phone =new ArrayList<>();
        pass=new ArrayList<>();

        storeDataInArray();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewDupl) ;

        customAdapter = new CustomAdapter(AAUser.this,this, usn, name,fee,dob,phone,address,route);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AAUser.this));

        DelAllStuData=(ImageButton) findViewById(R.id.del_entries_btn);
        DelAllStuData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AAUser.this);
                builder.setTitle("Delete all the Data?");
                builder.setMessage("Are you sure you want to delete all the users?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DelAllData();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    void storeDataInArray(){
        Cursor cursor=dh.readAllData3();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                usn.add(cursor.getString(0));
                name.add(cursor.getString(1));
                dob.add(cursor.getString(2));
                phone.add(cursor.getString(3));
                address.add(cursor.getString(4));
                fee.add(cursor.getString(5));
                route.add(cursor.getString(6));
                pass.add(cursor.getString(7));
            }

        }
        Collections.reverse(usn);
        Collections.reverse(name);
        Collections.reverse(dob);
        Collections.reverse(phone);
        Collections.reverse(address);
        Collections.reverse(fee);
        Collections.reverse(route);
        Collections.reverse(pass);
    }

    void DelAllData(){
        dh.DelAllStuData();
    }
}