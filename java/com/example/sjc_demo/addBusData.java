package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class addBusData extends AppCompatActivity {

    public EditText name,route,numPlate,imgurl;
    public Button add;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_bus_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name=(EditText) findViewById(R.id.name_bus);
        route=(EditText) findViewById(R.id.bus_route);
        numPlate=(EditText) findViewById(R.id.num_plate);
        imgurl=(EditText) findViewById(R.id.img_url);
        add=(Button)findViewById(R.id.add_data);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusDatabaseHelper bdh=new BusDatabaseHelper(addBusData.this);
                bdh.AddBusData(name.getText().toString(),route.getText().toString(),numPlate.getText().toString(),imgurl.getText().toString().trim());
                name.setText("");
                route.setText("");
                numPlate.setText("");
                imgurl.setText("");
            }
        });
    }
}