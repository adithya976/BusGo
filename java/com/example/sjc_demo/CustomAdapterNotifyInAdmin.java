package com.example.sjc_demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapterNotifyInAdmin extends RecyclerView.Adapter<CustomAdapterNotifyInAdmin.MyViewHolder> {
    public Context context;
    public ArrayList time,date,usn,name,msg,id;

    CustomAdapterNotifyInAdmin(Context context, ArrayList date, ArrayList time, ArrayList name,ArrayList usn,ArrayList msg,ArrayList id){
        this.context=context;
        this.id=id;
        this.date=date;
        this.time=time;
        this.usn=usn;
        this.name=name;
        this.msg=msg;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.notif_in_admin,parent,false);
        return new CustomAdapterNotifyInAdmin.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(msg.get(position).equals("❗⚠\uFE0F❗ EMERGENCY ❗⚠\uFE0F❗")){
            holder.title.setText("⚠\uFE0FEMERGENCY⚠\uFE0F");
            holder.title.setTypeface(null, Typeface.BOLD);
            holder.title.setTextColor(0xFFFF0000);
            holder.title.setTextSize(23);
            holder.name.setText("");
//            holder.imgView.setImageResource(R.drawable.);
//            Glide.with(context).load("https://www.freeiconspng.com/img/1562").into(holder.imgView);
        }else if(msg.get(position).equals("This is a quarterly reminder to send the buses for servicing.\nProper maintenance of the buses enhances the overall life of the bus.")){
            holder.title.setText("BUS SERVICE REMINDER");
            holder.title.setTypeface(null, Typeface.BOLD);
            holder.title.setTextSize(20);
            holder.name.setText("");
        }else{
            holder.name.setText(String.valueOf(name.get(position)));
            holder.name.setTextSize(23);
            holder.imgView.setImageResource(R.drawable.baseline_chat_24);
//            holder.imgView.setImageTintBlendMode(0xFFFF0000);
        }
        holder.date.setText(String.valueOf(date.get(position)));
        holder.time.setText(String.valueOf(time.get(position)));

        if(String.valueOf(msg.get(position)).isEmpty()){
            holder.title.setText("New Student User!");
            holder.title.setTypeface(null, Typeface.BOLD);
            holder.imgView.setImageResource(R.drawable.baseline_account_circle_green);
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,AANewUser.class);
                    context.startActivity(intent);
                    FeedbackDatabaseHelper fdh=new FeedbackDatabaseHelper(context);
                    fdh.onSeen(String.valueOf(id.get(position)));
                    date.remove(position);
                    time.remove(position);
                    name.remove(position);
                    msg.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }else{
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,InboxMsg.class);
                    context.startActivity(intent);
                    FeedbackDatabaseHelper fdh=new FeedbackDatabaseHelper(context);
                    fdh.onSeen(String.valueOf(id.get(position)));
                    date.remove(position);
                    time.remove(position);
                    name.remove(position);
                    msg.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,date,time,title;
        LinearLayout ll;
        ImageView imgView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.notif_name);
            time=itemView.findViewById(R.id.notif_time);
            date=itemView.findViewById(R.id.notif_date);
            title=itemView.findViewById(R.id.notif_title);
            imgView=itemView.findViewById(R.id.notif_imgView);
            ll=itemView.findViewById(R.id.linearLayoutNotification);
        }
    }
}
