package com.example.sjc_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BusDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME="BusInfo.db";
    public static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="bus_info";
    private static final String COLUMN_ID="Bus_id";
    private static final String COLUMN_BUSNAME="Bus_name";
    private static final String COLUMN_ROUTE="Bus_Route";
    private static final String COLUMN_NOPLATE="Bus_Num_Plate";
    private static final String COLUMN_IMGURL="Bus_Image";



    public BusDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_BUSNAME+" VARCHAR, "+COLUMN_NOPLATE+" VARCHAR, "+COLUMN_ROUTE+" VARCHAR, "+COLUMN_IMGURL+" VARCHAR);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    void AddBusData(String name,String route,String plate_no,String image_url){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_BUSNAME,name);
        cv.put(COLUMN_NOPLATE,plate_no);
        cv.put(COLUMN_ROUTE,route);
        cv.put(COLUMN_IMGURL, image_url);

        long result=db.insert(TABLE_NAME,null,cv);
        if(result==-1){
            Toast.makeText(context, "Bus couldn't be added.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Bus Added Successfully!", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    Cursor readData(){
        String query="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void deleteSingleRecord(String row_id){
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(TABLE_NAME,"Bus_id=?",new String[]{row_id});
        if(result==-1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteTable(){
        SQLiteDatabase db= this.getWritableDatabase();
        long result=db.delete(TABLE_NAME,null,null);
        if(result==-1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void editBusData(String Bus_ID, String bus_name,String bus_no, String bus_URL,String bus_route){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_IMGURL,bus_URL);
        cv.put(COLUMN_BUSNAME,bus_name);
        cv.put(COLUMN_NOPLATE,bus_no);
        cv.put(COLUMN_ROUTE,bus_route);
        long result=db.update(TABLE_NAME,cv,"Bus_id=?",new String[]{Bus_ID});
        if(result==-1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated the data successfully", Toast.LENGTH_SHORT).show();
        }
    }

}

