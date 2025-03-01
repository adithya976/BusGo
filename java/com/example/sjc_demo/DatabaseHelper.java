package com.example.sjc_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME="StudentsInfo.db";
    private static final int DATABASE_VERSION=2;
    private static final String TABLE_NAME="student_info";
    private static final String COLUMN_USN="USN";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_YEAR="year";
    private static final String COLUMN_PHONE="phone";
    private static final String COLUMN_ADDRESS="address";
    private static final String COLUMN_FEERECEIPT="fee_receipt";
    private static final String COLUMN_ROUTE="route";
    private static final String COLUMN_PASSWORD="password";
    private static final String COLUMN_VERIFY="verify";


    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+"("+COLUMN_USN+" VARCHAR PRIMARY KEY, "+COLUMN_NAME+" TEXT NOT NULL, "+COLUMN_YEAR+" SMALLINT, "+COLUMN_PHONE+" VARCHAR(50), "+COLUMN_ADDRESS+" VARCHAR, "+COLUMN_FEERECEIPT+" VARCHAR, "+COLUMN_ROUTE+" VARCHAR, "+COLUMN_PASSWORD+" VARCHAR, "+COLUMN_VERIFY+" INTEGER DEFAULT 0);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void add_data(String USN,String name, int year,int phone_no,String address, String route, String fee_receipt, String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();

        cv.put(COLUMN_USN, USN);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_PHONE, phone_no);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_ROUTE, route);
        cv.put(COLUMN_FEERECEIPT, fee_receipt);
        cv.put(COLUMN_PASSWORD, pass);

        long result=db.insert(TABLE_NAME,null,cv);
        if(result==-1){
            Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Registration Successful! Please wait till your info is verified by the Admin", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    Cursor readAllData(){
        String query="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_usn,String name,String address,String dob,String fee,String phone,String route){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_YEAR, dob);
        cv.put(COLUMN_FEERECEIPT, fee);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_ROUTE, route);


        long result= db.update(TABLE_NAME,cv,"USN=?",new String[]{row_usn});
        if(result==-1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated the data successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRecord(String row_usn){
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(TABLE_NAME,"USN=?",new String[]{row_usn});
        if(result==-1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }

    }
    void DelAllStuData(){
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(TABLE_NAME,null,null);
        if(result==-1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void onVerify(String row_usn){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(COLUMN_VERIFY,1);
        long result= db.update(TABLE_NAME,cv,"USN=?",new String[]{row_usn});
        if(result==-1){
            Toast.makeText(context, "Verification Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Verified successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor Select1Record(String usn){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_USN+"=?";
        Cursor cursor=db.rawQuery(query, new String[]{usn});
        return cursor;
    }

    Cursor readAllData2(){
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_VERIFY+" =0;";
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor readAllData3(){
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_VERIFY+" =1;";
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

}
