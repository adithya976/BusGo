package com.example.sjc_demo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterFeedback extends RecyclerView.Adapter<CustomAdapterFeedback.MyViewHolder> {

    private Context context;
    public ArrayList message,USN,name,route,date,time;

    CustomAdapterFeedback(Context context,ArrayList message,ArrayList USN,ArrayList name,ArrayList route,ArrayList date,ArrayList time){
        this.context=context;
        this.message=message;
        this.USN=USN;
        this.name=name;
        this.route=route;
        this.date=date;
        this.time=time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.row_messages_stuinbox,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        holder.usn.setText(String.valueOf(USN.get(position)));
        if(message.get(position).equals("❗⚠\uFE0F❗ EMERGENCY ❗⚠\uFE0F❗")){
            holder.msg.setTextSize(20);
            holder.msg.setTypeface(null, Typeface.BOLD);
            holder.msg.setTextColor(0xFFFF0000);
            holder.msg.setText("❗⚠\uFE0F❗EMERGENCY❗⚠\uFE0F❗");
        }else{
            holder.msg.setText(String.valueOf(message.get(position)));
        }
        holder.route.setText(String.valueOf(route.get(position)));
        holder.DATE.setText(String.valueOf(date.get(position)));
        holder.TIME.setText(String.valueOf(time.get(position)));
    }

    @Override
    public int getItemCount() {
        return USN.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,usn,msg,route,DATE,TIME;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.inb_name);
            usn=itemView.findViewById(R.id.inb_usn);
            msg=itemView.findViewById(R.id.inb_stu_msg);
            route=itemView.findViewById(R.id.inb_route);
            DATE=itemView.findViewById(R.id.date1);
            TIME=itemView.findViewById(R.id.time1);
        }
    }
}
