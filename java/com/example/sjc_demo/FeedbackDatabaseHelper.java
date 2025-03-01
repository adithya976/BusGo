package com.example.sjc_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class FeedbackDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final int DATABASE_VERSION=2;
    public static final String DATABASE_NAME="Feedback.db";
    public static final String TABLE_NAME="feedback";
    public static final String COLUMN_USN="usn";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_MESSAGE="message";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_ROUTE="route";
    public static final String COLUMN_DATE="date";
    public static final String COLUMN_TIME="time";
    public static final String COLUMN_SEEN="seen";
    public static final String COLUMN_CLEARforStu="clrStuMsg";

    public FeedbackDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_USN+" VARCHAR, "+COLUMN_NAME+" VARCHAR, "+COLUMN_MESSAGE+" TEXT, "+COLUMN_ROUTE+" TEXT, "+COLUMN_DATE+" DEFAULT CURRENT_DATE, "+COLUMN_TIME+" DEFAULT CURRENT_TIME, "+COLUMN_SEEN+" INTEGER DEFAULT 0, "+COLUMN_CLEARforStu+" INTEGER DEFAULT 0);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    void addMessage(String usn,String name,String message,String route){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_USN,usn);
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_MESSAGE,message);
        cv.put(COLUMN_ROUTE,route);

        long result=db.insert(TABLE_NAME,null,cv);
        if(result==-1){
            Toast.makeText(context, "Message couldn't be sent.", Toast.LENGTH_LONG).show();
        }else{
            if(message.equals("")){
                return;
            }else{
                Toast.makeText(context, "Message sent to the admin.", Toast.LENGTH_LONG).show();
            }

        }
        db.close();
    }

    Cursor ReadTheData(){
        SQLiteDatabase db=this.getReadableDatabase();
//        String query="SELECT * FROM "+TABLE_NAME;
        String query="SELECT * FROM "+TABLE_NAME +" WHERE message!=\"\"";

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

    void onSeen(String rowID){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SEEN,1);
        db.update(TABLE_NAME,cv,"id=?",new String[]{rowID});
    }

    Cursor ReadTheData2(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_SEEN+"=0";

        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void onSeen2(){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SEEN,1);
        long result=db.update(TABLE_NAME,cv,null,null);
        if(result==-1){
            Toast.makeText(context, "Failed to clear the notifications", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "All notifications cleared", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor ReadMsg2(String row_usn){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_CLEARforStu+"=0 AND "+COLUMN_USN+"=?;";

        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query, new String[]{row_usn});
        }
        return cursor;
    }

    void ClearStuMsg(String row_usn){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_CLEARforStu,1);
        db.update(TABLE_NAME,cv,"usn=?",new String[]{row_usn});
    }

}
