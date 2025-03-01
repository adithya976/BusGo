package com.example.sjc_demo;

import android.annotation.SuppressLint;
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

public class updateBusData extends AppCompatActivity {

    EditText b_name,b_route,b_no,b_img;
    Button updateBtn;
    String busName,busNum,busImage,busRoute,busID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_bus_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b_name=(EditText) findViewById(R.id.name_bus_up);
        b_route=(EditText) findViewById(R.id.bus_route_up);
        b_no=(EditText) findViewById(R.id.num_plate_up);
        b_img=(EditText) findViewById(R.id.img_url_up);
        updateBtn=(Button) findViewById(R.id.update_data_bus);

        getAndSetIntentData();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusDatabaseHelper bdh=new BusDatabaseHelper(updateBusData.this);
                bdh.editBusData(busID,b_name.getText().toString(),b_no.getText().toString(),b_img.getText().toString(),b_route.getText().toString());
            }
        });
    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("B_name")&&getIntent().hasExtra("B_URL")&&getIntent().hasExtra("B_route")&&getIntent().hasExtra("B_num")&&getIntent().hasExtra("B_ID")){
            //get text from getIntent and store it as Strings.
            busName=getIntent().getStringExtra("B_name");
            busImage=getIntent().getStringExtra("B_URL");
            busRoute=getIntent().getStringExtra("B_route");
            busNum=getIntent().getStringExtra("B_num");
            busID=getIntent().getStringExtra("B_ID");

            //set the values of those Strings to the editText variables so that the Actual text is displayed on the EditText places in the UpdateActivity
            b_name.setText(busName);
            b_img.setText(busImage);
            b_route.setText(busRoute);
            b_no.setText(busNum);

        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
}