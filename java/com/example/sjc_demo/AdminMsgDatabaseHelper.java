package com.example.sjc_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AdminMsgDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME="AdminMsg.db";
    public static final int DATABASE_VERSION=2;
    public static final String TABLE_NAME="Admin_Msg";
    public static final String COLUMN_ID="ID";
    public static final String COLUMN_MSG="message";
    public static final String COLUMN_DATE="date";
    public static final String COLUMN_TIME="time";
    public static final String COLUMN_CLEARforAdmin="clrAdminMsg";

    public AdminMsgDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_MSG+" TEXT, "+COLUMN_DATE+" DEFAULT CURRENT_DATE, "+COLUMN_TIME+" DEFAULT CURRENT_TIME, "+COLUMN_CLEARforAdmin+" INTEGER DEFAULT 0);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    void AddMessageAdmin(String message){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_MSG,message);
        long result=db.insert(TABLE_NAME,null,cv);
        if(result==-1){
            Toast.makeText(context, "Message couldn't be sent", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Message sent successfully", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    Cursor ReadMsg(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;

        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void DeleteAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(TABLE_NAME,null,null);
        if(result==-1){
            Toast.makeText(context, "Failed to delete the messages", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor ReadMsg2(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_CLEARforAdmin+"=0;";

        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void ClearAdminMsg(){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_CLEARforAdmin,1);
        db.update(TABLE_NAME,cv,null,null);
    }


}
