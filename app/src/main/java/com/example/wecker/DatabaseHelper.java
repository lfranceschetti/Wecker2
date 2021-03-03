package com.example.wecker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.net.IDN;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    //This Class regulates all the interactions with the SQLlite database

    private static final String TABLE_NAME = "DREAMS_TABLE";
    private static final String COL1 = "ID";
    private static final String COL2 = "TITLEFIELD";
    private static final String COL3 = "TEXTFIELD";
    private static final String COL4 = "DATEFIELD";
    private static final String COL5 = "LUCIDFIELD";
    ArrayList<Integer> idData;

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);

        final String TAG = "DatabaseHelper";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL2 + " TEXT, "+ COL3 + " TEXT, "+ COL4 + " TEXT, "+ COL5 + " INTEGER)";
        db.execSQL(createTable);
        addData("Traum", "Hier ist ein gewöhnlicher Traum", "Monday 01.01.2000", 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void changeData(String title, String text, int listPosition, int lucidity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor data = getData();
        idData = new ArrayList<>();
        while (data.moveToNext()) {
            idData.add(data.getInt(0));
        }

        contentValues.put(COL2, title);
        contentValues.put(COL3, text);
        contentValues.put(COL5, lucidity);
        db.update(TABLE_NAME, contentValues,"ID = " + idData.get(listPosition), null);

    }

    public boolean addData(String title, String text, String currentDate, int lucidity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, title);
        contentValues.put(COL3, text);
        contentValues.put(COL4, currentDate);
        contentValues.put(COL5, lucidity);


        long result = db.insert(TABLE_NAME, null,contentValues);
        if (result == -1) {
            return false;
        }else{
            return true;
        }

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = " + id, null);
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
        addData("Traum", "Hier ist ein gewöhnlicher Traum", "Monday 01.01.2000", 0);
    }

}
