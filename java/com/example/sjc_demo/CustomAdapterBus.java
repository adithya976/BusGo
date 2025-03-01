package com.example.sjc_demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterBus extends RecyclerView.Adapter<CustomAdapterBus.MyViewHolder> {

    private Context context;
    private ArrayList bus_name,bus_no,bus_id,bus_route,bus_img;
    Activity activity;
    CustomAdapterBus( Activity activity,Context context,ArrayList bus_name,ArrayList bus_no,ArrayList bus_id,ArrayList bus_route,ArrayList bus_img){
        this.context=context;
        this.activity=activity;
        this.bus_name=bus_name;
        this.bus_id=bus_id;
        this.bus_no=bus_no;
        this.bus_route=bus_route;
        this.bus_img=bus_img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.bus_data_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        holder.id.setText(String.valueOf(bus_id.get(position)));
        holder.name.setText(String.valueOf(bus_name.get(position)));
        holder.no.setText(String.valueOf(bus_no.get(position)));
        holder.route.setText(String.valueOf(bus_route.get(position)));

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, updateBusData.class);
                intent.putExtra("B_name",String.valueOf(bus_name.get(position)));
                intent.putExtra("B_num",String.valueOf(bus_no.get(position)));
                intent.putExtra("B_route",String.valueOf(bus_route.get(position)));
                intent.putExtra("B_URL",String.valueOf(bus_img.get(position)));
                intent.putExtra("B_ID",String.valueOf(bus_id.get(position)));

                activity.startActivityForResult(intent,1);
            }
        });

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete the bus "+bus_name.get(position)+" ("+bus_id.get(position)+") ?");
                builder.setMessage("Are you sure you want to delete the bus "+bus_id.get(position)+" ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BusDatabaseHelper bdh=new BusDatabaseHelper(context);
                        bdh.deleteSingleRecord(String.valueOf(bus_id.get(position)));
                        bus_name.remove(position);
                        bus_no.remove(position);
                        bus_id.remove(position);
                        bus_route.remove(position);
                        notifyItemRemoved(position);
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
    public int getItemCount() {
        return bus_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,id,no,route;
        ImageButton editBtn, delBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.Bus_ID);
            name=itemView.findViewById(R.id.Bus_name_txt);
            no=itemView.findViewById(R.id.bus_no_plate);
            route=itemView.findViewById(R.id.bus_route_txt);
            editBtn=itemView.findViewById(R.id.edit_bus_btn);
            delBtn=itemView.findViewById(R.id.delete_bus_btn);
        }
    }
}
