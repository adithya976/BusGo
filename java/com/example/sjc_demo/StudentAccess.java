package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class StudentAccess extends AppCompatActivity {

    public Button register_button;
    public Button login_button;
    EditText usn,pass;
    DatabaseHelper dh;
    ArrayList<String> usnum,pasw,onVer,sturoute;
    String USN,name;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_access);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usn=(EditText) findViewById(R.id.username1);
        pass=(EditText) findViewById(R.id.password1);

        dh=new DatabaseHelper(StudentAccess.this);

        usnum=new ArrayList<>();
        pasw=new ArrayList<>();
        sturoute=new ArrayList<>();
        onVer=new ArrayList<>();

        register_button = (Button) findViewById(R.id.regisBtn);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentAccess.this, StudentAccessRegister.class);
                startActivity(intent);
            }
        });


        USN_PassInArray();

        login_button = (Button) findViewById(R.id.lgnBtn);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=0;
                USN_PassInArray();
                if(usn.getText().toString().equals("")||pass.getText().toString().equals("")){
                    Toast.makeText(StudentAccess.this, "Entering both the fields are mandatory.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for(int i=0;i<usnum.size();i++){
                    if(usn.getText().toString().equals(usnum.get(i))){
                        count++; break;
                    }
                }
                if(count==0){
                    Toast.makeText(StudentAccess.this, "User not registered.", Toast.LENGTH_SHORT).show();
                    return;
                }


                for(int i=0;i<usnum.size();i++){
                    if(usn.getText().toString().equals(usnum.get(i)) && pass.getText().toString().equals(pasw.get(i))){
                        if(Integer.valueOf(onVer.get(i))==0){
                            Toast.makeText(StudentAccess.this, "User not verified yet, please wait till your info is verified by the admin.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            usn.setText("");
                            pass.setText("");
                            Cursor cursor2=dh.Select1Record(usnum.get(i));
                            if(cursor2.moveToFirst()){
                                USN=cursor2.getString(0);
                                name=cursor2.getString(1);
                            }
                            Intent intent = new Intent(StudentAccess.this, StudentActivity.class);
                            intent.putExtra("route",sturoute.get(i));
                            intent.putExtra("usn",USN);
                            intent.putExtra("name",name);
                            startActivity(intent);
                            Toast.makeText(StudentAccess.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                }
                Toast.makeText(StudentAccess.this, "Login Failed! Incorrect USN or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void USN_PassInArray(){
        Cursor cursor=dh.readAllData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                usnum.add(cursor.getString(0));
                sturoute.add(cursor.getString(6));
                pasw.add(cursor.getString(7));
                onVer.add(cursor.getString(8));
            }

        }
    }
}

