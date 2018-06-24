package com.example.kotytoger.memgame2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gargulio on 6/11/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Score.db";
    public static final String TABLE_NAME = "score_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SCORE";
    public static final String COL_4 = "LOCATION";



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, SCORE TEXT, LOCATION TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String Score, String city){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,Score);
        contentValues.put(COL_4,city);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " order by " + COL_3 +" desc",null);
        deleteBottom();
        return res;
    }

    public void deleteBottom(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete From "+TABLE_NAME+"  where "+ COL_1  + " not in (Select "+ COL_1 +" from "+ TABLE_NAME + " order by " + COL_1 +" limit 10)");

    }

}
