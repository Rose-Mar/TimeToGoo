package com.example.timetogo.db;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.timetogo.db.entity.ListItem;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "items_db";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(ListItem.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ListItem.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }

    // Insert Data into Database

public long insertContact(String name, String time){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(ListItem.COLUMN_NAME, name);
        values.put(ListItem.COLUMN_TIME, time);
        long id = db.insert(ListItem.TABLE_NAME, null, values );


        db.close();
        return id;
}



//Getting Items from DataBase

    public ListItem getListItem (long id){
        SQLiteDatabase db = this. getReadableDatabase();
        Cursor cursor = db.query(ListItem.TABLE_NAME,
                new String[]{
                        ListItem.COLUMN_ID,
                        ListItem.COLUMN_NAME,
                        ListItem.COLUMN_TIME},
                ListItem.COLUMN_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null,
                null,
                null,
                null);


        if(cursor !=null)
            cursor.moveToFirst();

        ListItem listItem = new ListItem(
                cursor.getString(cursor.getColumnIndex(ListItem.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(ListItem.COLUMN_TIME)),
                cursor.getInt(cursor.getColumnIndex(ListItem.COLUMN_ID))
        );
        cursor.close();
        return listItem;

    }

    //Getting all Items

    public ArrayList<ListItem>


}
