package com.example.sjc_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterMessageAdmin extends RecyclerView.Adapter<CustomAdapterMessageAdmin.MyViewHolder>{
    private Context context;
    public ArrayList message,date,time;

    CustomAdapterMessageAdmin(Context context,ArrayList message,ArrayList date,ArrayList time){
        this.context=context;
        this.message=message;
        this.date=date;
        this.time=time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.admin_msg_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.msg.setText(String.valueOf(message.get(position)));
        holder.DATE.setText(String.valueOf(date.get(position)));
        holder.TIME.setText(String.valueOf(time.get(position)));
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView msg,DATE,TIME;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg=itemView.findViewById(R.id.Messageholder);
            DATE=itemView.findViewById(R.id.date2);
            TIME=itemView.findViewById(R.id.time2);
        }
    }
}
