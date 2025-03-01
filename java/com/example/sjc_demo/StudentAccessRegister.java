package com.example.sjc_demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.PendingIntentCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class StudentAccessRegister extends AppCompatActivity {

    String[] route={"College to D1","College to D2","College to D3","College to D4","College to D5"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    EditText name,phone,usn,pass,address,year,fee_receipt;
    Button regis_final;
    String final_route;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_access_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name=(EditText) findViewById(R.id.r_name);
        year=(EditText) findViewById(R.id.r_date);
        phone=(EditText) findViewById(R.id.r_phone);
        address=(EditText) findViewById(R.id.r_address);
        pass=(EditText) findViewById(R.id.r_pass);
        usn=(EditText) findViewById(R.id.r_usn);
        fee_receipt=(EditText) findViewById(R.id.r_fee);
        regis_final=(Button) findViewById(R.id.final_regis_Btn);

//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
//            if(ContextCompat.checkSelfPermission(StudentAccessRegister.this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(StudentAccessRegister.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
//            }
//        }


        autoCompleteTextView= findViewById(R.id.auto_complete_textview);
        adapterItems=new ArrayAdapter<String> (this,R.layout.list_route, route);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int position,long l){
                final_route = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(StudentAccessRegister.this, "Route "+ final_route, Toast.LENGTH_SHORT).show();
            }

        });


        regis_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dh = new DatabaseHelper(StudentAccessRegister.this);
                if(usn.getText().toString()==null||name.getText().toString()==null||year.getText().toString()==null||phone.getText().toString()==null||address.getText().toString()==null||final_route==null||fee_receipt.getText().toString()==null||pass.getText().toString()==null){
                    Toast.makeText(StudentAccessRegister.this, "Entering all the fields are mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                }
                dh.add_data(usn.getText().toString(),name.getText().toString(),Integer.valueOf(year.getText().toString()),Integer.valueOf(phone.getText().toString()),address.getText().toString(),final_route,fee_receipt.getText().toString(),pass.getText().toString());
                FeedbackDatabaseHelper fdh=new FeedbackDatabaseHelper(StudentAccessRegister.this);
                fdh.addMessage(usn.getText().toString(),name.getText().toString(),"","");
                usn.setText("");
                name.setText("");
                year.setText("");
                phone.setText("");
                address.setText("");
                fee_receipt.setText("");
                pass.setText("");

//                final_route=;
//                makeNotification();
            }
        });

    }

//    public void makeNotification(){
//        String channelId="Regis_Notification";
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(), channelId);
//        builder.setSmallIcon(R.drawable.baseline_directions_bus_24)
//                .setContentTitle("New User")
//                .setContentText("Check the details and perform any action")
//                .setAutoCancel(true)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        Intent intent=new Intent(getApplicationContext(),AANewUser.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_MUTABLE);
//        builder.setContentIntent(pendingIntent);
//        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//        if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
//            NotificationChannel notificationChannel=notificationManager.getNotificationChannel(channelId);
//            if(notificationChannel==null){
//                int importance=NotificationManager.IMPORTANCE_HIGH;
//                notificationChannel=new NotificationChannel(channelId,"Some Description", importance);
//                notificationChannel.setLightColor(Color.BLUE);
//                notificationChannel.enableVibration(true);
//                notificationManager.createNotificationChannel(notificationChannel);
//            }
//        }
//
//        notificationManager.notify(0, builder.build());
//
//    }



}