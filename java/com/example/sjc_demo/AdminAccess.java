package com.example.sjc_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminAccess extends AppCompatActivity {

    public TextView user_name;
    public TextView password;
    public Button lgn_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_access);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user_name = (TextView) findViewById(R.id.editTextText2);
        password = (TextView) findViewById(R.id.editTextTextPassword2);
        lgn_btn = (Button) findViewById(R.id.login_admin_btn);

        lgn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //admin and admin is the usrname and password
                if(user_name.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    user_name.setText("");
                    password.setText("");
                    Intent intent = new Intent(AdminAccess.this,AdminActivity.class);
                    startActivity(intent);
                    Toast.makeText(AdminAccess.this, "Login Successful!", Toast.LENGTH_SHORT).show();}
                else Toast.makeText(AdminAccess.this, "Login Failed! Incorrect Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}