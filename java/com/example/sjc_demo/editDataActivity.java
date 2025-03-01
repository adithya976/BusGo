package com.example.sjc_demo;

import android.content.Intent;
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

public class editDataActivity extends AppCompatActivity {

    EditText name,dob,address,fee,route,phone;
    Button updateBtn;
    String Name,Address,Phone,Dob,Route,Fee,USN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name=(EditText) findViewById(R.id.u_name);
        dob=(EditText) findViewById(R.id.u_date);
        address=(EditText) findViewById(R.id.u_address);
        fee=(EditText) findViewById(R.id.u_fee);
        phone=(EditText) findViewById(R.id.u_phone);
        route=(EditText) findViewById(R.id.route_edit);
        updateBtn=(Button) findViewById(R.id.final_update_Btn);

        getAndSetIntentData();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dh=new DatabaseHelper(editDataActivity.this);
                dh.updateData(USN,name.getText().toString(),address.getText().toString(),dob.getText().toString(),fee.getText().toString(),phone.getText().toString(),route.getText().toString());
            }
        });

    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("FullName")&&getIntent().hasExtra("PhoneNo")&&getIntent().hasExtra("Dob")&&getIntent().hasExtra("FeeReceipt")&&getIntent().hasExtra("Address")&&getIntent().hasExtra("Route")){
            //get text from getIntent and store it as Strings.
            Name=getIntent().getStringExtra("FullName");
            Phone=getIntent().getStringExtra("PhoneNo");
            Dob=getIntent().getStringExtra("Dob");
            Address=getIntent().getStringExtra("Address");
            Fee=getIntent().getStringExtra("FeeReceipt");
            USN=getIntent().getStringExtra("UserName");
            Route=getIntent().getStringExtra("Route");

            //set the values of those Strings to the editText variables so that the Actual text is displayed on the EditText places in the UpdateActivity
            name.setText(Name);
            phone.setText(Phone);
            dob.setText(Dob);
            address.setText(Address);
            fee.setText(Fee);
            route.setText(Route);

        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
}