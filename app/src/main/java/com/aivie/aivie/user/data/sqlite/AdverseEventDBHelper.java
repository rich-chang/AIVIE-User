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

public class AdverseEventDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "aivie_user.db";
    private static final String AEH_TABLE_NAME = "AdverseEventHistory";
    private static final String AEH_COLUMN_ID = "id";
    public static final String AEH_COLUMN_USER_ID = "userId";
    public static final String AEH_COLUMN_EVENT_NAME = "eventName";
    public static final String AEH_COLUMN_EVENT_HAPPENED = "eventHappened";   // happened time
    public static final String AEH_COLUMN_EVENT_DURATION = "eventDuration";
    public static final String AEH_COLUMN_EVENT_REPORTED = "eventReported";   // now time
    private HashMap hp;

    //public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
    public AdverseEventDBHelper(@Nullable Context context) {
        //super(context, name, factory, version);
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Trigger if db file is not found
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table AdverseEventHistory " +
                        "(id integer primary key, userId text, eventName text, eventHappened text, eventDuration text, eventReported text)"
        );
    }

    // Trigger if column/structure changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS AdverseEventHistory");
        onCreate(sqLiteDatabase);
    }

    public void removeAll() {

        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AEH_TABLE_NAME, null, null);
    }

    public boolean insertEvent (String userId, String eventName, String eventHappened, String eventDuration) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AEH_COLUMN_USER_ID, userId);
        contentValues.put(AEH_COLUMN_EVENT_NAME, eventName);
        contentValues.put(AEH_COLUMN_EVENT_HAPPENED, eventHappened);
        contentValues.put(AEH_COLUMN_EVENT_DURATION, eventDuration);

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_FULL, Locale.US);
        contentValues.put(AEH_COLUMN_EVENT_REPORTED, sdf.format(new Date())); // now time

        // TODO: Error handling
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

    public ArrayList<String> getAlluserId() {
        ArrayList<String> eventList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from AdverseEventHistory", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            eventList.add(res.getString(res.getColumnIndex(AEH_COLUMN_USER_ID)));
            res.moveToNext();
        }

        return eventList;
    }

    public ArrayList<String> getAllEventName() {
        ArrayList<String> eventList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from AdverseEventHistory", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            eventList.add(res.getString(res.getColumnIndex(AEH_COLUMN_EVENT_NAME)));
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
            eventList.add(res.getString(res.getColumnIndex(AEH_COLUMN_EVENT_HAPPENED)));
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
            eventList.add(res.getString(res.getColumnIndex(AEH_COLUMN_EVENT_DURATION)));
            res.moveToNext();
        }
        return eventList;
    }
}
