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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class busData extends AppCompatActivity {

    public FloatingActionButton addBus;
    public ImageButton DelAll;
    RecyclerView recyclerView;
    BusDatabaseHelper bdh;
    ArrayList<String> bus_name,bus_no_plate,bus_id,bus_route,bus_img;
    CustomAdapterBus customAdapterBus;
    public int count=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bus_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addBus=(FloatingActionButton) findViewById(R.id.floatingActionButton3);
        addBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(busData.this,addBusData.class);
                startActivity(intent);
            }
        });
        bdh=new BusDatabaseHelper(busData.this);
        bus_id=new ArrayList<>();
        bus_name=new ArrayList<>();
        bus_no_plate=new ArrayList<>();
        bus_route=new ArrayList<>();
        bus_img=new ArrayList<>();

        StoreDataInArray();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView2) ;

        customAdapterBus = new CustomAdapterBus(busData.this,this, bus_name,bus_no_plate,bus_id,bus_route,bus_img);
        recyclerView.setAdapter(customAdapterBus);
        recyclerView.setLayoutManager(new LinearLayoutManager(busData.this));


        DelAll=(ImageButton) findViewById(R.id.DeleteAllBtn);
        DelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(busData.this);
                builder.setTitle("Delete all the Data?");
                builder.setMessage("Are you sure you want to delete all the buses?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete_all();
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

    void StoreDataInArray(){
        Cursor cursor=bdh.readData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                bus_id.add(cursor.getString(0));
                bus_name.add(cursor.getString(1));
                bus_no_plate.add(cursor.getString(2));
                bus_route.add(cursor.getString(3));
                bus_img.add(cursor.getString(4));
            }
        }

    }
    void delete_all(){
        bdh.deleteTable();
    }
}