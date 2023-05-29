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
        sqLiteDatabase.execSQL(ListItem.CREATE_TABLE );
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ListItem.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }

    // Insert Data into Database

public long insertContact(String name, int time){
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
                cursor.getString(cursor.getColumnIndexOrThrow(ListItem.COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(ListItem.COLUMN_TIME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(ListItem.COLUMN_ID))

        );
        cursor.close();
        return listItem;

    }

    //Getting all Items

    public ArrayList<ListItem> getAkkItems(){
        ArrayList<ListItem> listItems = new ArrayList<>();


        String selectQuery = "SELECT * FROM " + ListItem.TABLE_NAME + " ORDER BY " +
                ListItem.COLUMN_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                ListItem listItem = new ListItem();
                listItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ListItem.COLUMN_ID)));
                listItem.setNameActivity(cursor.getString(cursor.getColumnIndexOrThrow(ListItem.COLUMN_NAME)));
                listItem.setTimeActivity(cursor.getInt(cursor.getColumnIndexOrThrow(ListItem.COLUMN_TIME)));

                listItems.add(listItem);
            }while(cursor.moveToNext());
        }
        db.close();
        return listItems;
    }

    public int updateListItem(ListItem listItem) {
     SQLiteDatabase db = this.getWritableDatabase();

     ContentValues values = new ContentValues();
     values.put(ListItem.COLUMN_NAME, listItem.getNameActivity());
     values.put(ListItem.COLUMN_TIME, listItem.getTimeActivity());

     return db.update(ListItem.TABLE_NAME, values, ListItem.COLUMN_ID+ " =? ",
             new String[]{String.valueOf(listItem.getId())});
    }



    public void deleteItem(ListItem listItem){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ListItem.TABLE_NAME, ListItem.COLUMN_ID + " = ?",
                new String[]{String.valueOf(listItem.getId())});

        db.close();
    }




    public int timeLeft(){
        return 0;
    }


}
