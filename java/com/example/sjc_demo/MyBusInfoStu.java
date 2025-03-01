package com.example.sjc_demo;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class MyBusInfoStu extends AppCompatActivity {

    TextView BusName,BusNum,BusRoute;
    ImageView imgView;
    String route;
    ArrayList<String> stuRoute,busRoute,busName,busID,busNum,busImgUrl;
    DatabaseHelper dh;
    BusDatabaseHelper bdh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_bus_info_stu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        route=getIntent().getStringExtra("route");
        BusName=(TextView) findViewById(R.id.bname);
        BusNum=(TextView) findViewById(R.id.bnumber);
        BusRoute=(TextView) findViewById(R.id.broute);
        imgView=(ImageView) findViewById(R.id.imageView4);
        bdh=new BusDatabaseHelper(MyBusInfoStu.this);
        dh=new DatabaseHelper(MyBusInfoStu.this);
        stuRoute=new ArrayList<>();
        busRoute=new ArrayList<>();
        busName=new ArrayList<>();
        busID=new ArrayList<>();
        busNum=new ArrayList<>();
        busImgUrl=new ArrayList<>();
        PutRouteinArray();
        CheckRouteAndAssignValues();
    }

    void PutRouteinArray(){
        Cursor cursor1= dh.readAllData();
        Cursor cursor2=bdh.readData();
        if(cursor1.getCount()==0||cursor2.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor1.moveToNext()){
                stuRoute.add(cursor1.getString(6).trim());
            }
            while(cursor2.moveToNext()){
                busID.add(cursor2.getString(0));
                busName.add(cursor2.getString(1));
                busNum.add(cursor2.getString(2));
                busRoute.add(cursor2.getString(3).trim());
                busImgUrl.add(cursor2.getString(4).trim());
            }
        }
    }

    void CheckRouteAndAssignValues(){
        for(int i=0;i<busRoute.size();i++){
                if(("College to "+busRoute.get(i)).equals(route)){
                    BusName.setText(busName.get(i));
                    BusNum.setText(busNum.get(i));
                    BusRoute.setText("College to "+busRoute.get(i));
                    Glide.with(this).load(busImgUrl.get(i)).into(imgView);
                }
        }
    }
}