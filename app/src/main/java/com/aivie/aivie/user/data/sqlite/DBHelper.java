package com.aivie.aivie.user.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.aivie.aivie.user.data.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "aivie_user.db";
    private static final String AEH_TABLE_NAME = "AdverseEventHistory";
    private static final String AEH_COLUMN_ID = "id";
    public static final String AEH_COLUMN_NAME = "name";
    public static final String AEH_COLUMN_HAPPENED = "happened";   // happened time
    public static final String AEH_COLUMN_DURATION = "duration";
    public static final String AEH_COLUMN_REPORTED = "reported";   // now time
    private HashMap hp;

    //public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
    public DBHelper(@Nullable Context context) {
        //super(context, name, factory, version);
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Trigger if db file is not found
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table AdverseEventHistory " +
                        "(id integer primary key, name text, happened text, duration text, reported text)"
        );
    }

    // Trigger if column/structure changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS AdverseEventHistory");
        onCreate(sqLiteDatabase);
    }

    public boolean insertEvent (String name, String happened, String duration) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AEH_COLUMN_NAME, name);
        contentValues.put(AEH_COLUMN_HAPPENED, happened);
        contentValues.put(AEH_COLUMN_DURATION, duration);

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_FULL, Locale.US);
        contentValues.put(AEH_COLUMN_REPORTED, sdf.format(new Date())); // now time

        db.insert(AEH_TABLE_NAME, null, contentValues);

        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from AdverseEventHistory where id=" + id + "", null );
        return res;
    }

    public Integer deleteData (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(AEH_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, AEH_TABLE_NAME);
        return numRows;
    }

    public ArrayList<String> getAllEventName() {
        ArrayList<String> eventList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from AdverseEventHistory", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            eventList.add(res.getString(res.getColumnIndex(AEH_COLUMN_NAME)));
            Log.i(Constant.TAG, "eventList: " +  eventList.get(eventList.size()-1));
            res.moveToNext();
        }

        return eventList;
    }

    public ArrayList<String> getAllEventHappenedDate() {
        ArrayList<String> eventList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from AdverseEventHistory", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            eventList.add(res.getString(res.getColumnIndex(AEH_COLUMN_HAPPENED)));
            res.moveToNext();
        }
        return eventList;
    }

    public ArrayList<String> getAllEventDuration() {
        ArrayList<String> eventList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from AdverseEventHistory", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            eventList.add(res.getString(res.getColumnIndex(AEH_COLUMN_DURATION)));
            res.moveToNext();
        }
        return eventList;
    }
}
