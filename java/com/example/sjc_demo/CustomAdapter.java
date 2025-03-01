package com.example.sjc_demo;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList usn,name,fee,dob,phone,address,route;
    Activity activity;

    CustomAdapter(Activity activity, Context context, ArrayList usn, ArrayList name,ArrayList fee,ArrayList dob,ArrayList phone,ArrayList address,ArrayList route){
        this.activity=activity;
        this.context=context;
        this.usn=usn;
        this.name=name;
        this.phone=phone;
        this.dob=dob;
        this.fee=fee;
        this.address=address;
        this.route=route;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {

        holder.name.setText(String.valueOf(name.get(position)));
        holder.usn.setText(String.valueOf(usn.get(position)));
        holder.address.setText(String.valueOf(address.get(position)));
        holder.route.setText(String.valueOf(route.get(position)));
        holder.dob.setText(String.valueOf(dob.get(position)));
        holder.phone.setText(String.valueOf(phone.get(position)));
        holder.fee.setText(String.valueOf(fee.get(position)));
        holder.verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dh=new DatabaseHelper(context);
                dh.onVerify(String.valueOf(usn.get(position)));
                holder.verifyBtn.setText("Verified!");
                holder.verifyBtn.setWidth(300);
                holder.verifyBtn.setEnabled(false);
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, editDataActivity.class);
                intent.putExtra("UserName",String.valueOf(usn.get(position)));
                intent.putExtra("FullName",String.valueOf(name.get(position)));
                intent.putExtra("Address",String.valueOf(address.get(position)));
                intent.putExtra("Dob",String.valueOf(dob.get(position)));
                intent.putExtra("PhoneNo",String.valueOf(phone.get(position)));
                intent.putExtra("FeeReceipt",String.valueOf(fee.get(position)));
                intent.putExtra("Route",String.valueOf(route.get(position)));

                activity.startActivityForResult(intent,1);
            }
        });
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete "+name.get(position)+" ("+usn.get(position)+") ?");
                builder.setMessage("Are you sure you want to delete the user "+usn.get(position)+" ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper dh=new DatabaseHelper(context);
                        dh.deleteOneRecord(String.valueOf(usn.get(position)));
                        usn.remove(position);
                        name.remove(position);
                        fee.remove(position);
                        dob.remove(position);
                        phone.remove(position);
                        address.remove(position);
                        route.remove(position);
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
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,usn,address,route,dob,fee,phone;
        Button verifyBtn;
        ImageButton editBtn, delBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_txt);
            usn=itemView.findViewById(R.id.usn_txt);
            address=itemView.findViewById(R.id.address_txt);
            route=itemView.findViewById(R.id.route_txt);
            dob=itemView.findViewById(R.id.dob_txt);
            fee=itemView.findViewById(R.id.fee_txt);
            phone=itemView.findViewById(R.id.phone_txt);
            verifyBtn=itemView.findViewById(R.id.verify_btn);
            editBtn=itemView.findViewById(R.id.edit_btn);
            delBtn=itemView.findViewById(R.id.delete_btn);
        }
    }
}

