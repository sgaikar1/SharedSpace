package com.emagicindia.realeastate.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.emagicindia.realeastate.model.CityArrayItem;

import java.util.ArrayList;

/**
 * Created by dell on 20/08/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String TAG = DatabaseHelper.class.getSimpleName();
    // Database Info
    private static final String DATABASE_NAME = "realEstateDB";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CITY = "city";

    // Post Table Columns
    private static final String KEY_CITY_ID = "id";
    private static final String KEY_CITY_NAME = "name";
    private static final String KEY_CITY_ACTIVE_STATUS = "active_status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_CITY +
                "(" +
                KEY_CITY_ID + " INTEGER, " + // Define a primary key
                KEY_CITY_NAME + " TEXT, " +
                KEY_CITY_ACTIVE_STATUS + " TEXT" +
                ")";

        db.execSQL(CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
            onCreate(db);
        }
    }

    public void addCity(ArrayList<CityArrayItem> cityItems) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
            for (int i= 0 ; i<cityItems.size(); i++) {
                values.put(KEY_CITY_ID, cityItems.get(i).getCityId());
                values.put(KEY_CITY_NAME, cityItems.get(i).getCityName());
                values.put(KEY_CITY_ACTIVE_STATUS, cityItems.get(i).getACTIVE());
                db.insert(TABLE_CITY, null, values);
            }
    }

    public int getCityTableCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int cnt  = (int) DatabaseUtils.queryNumEntries(db, TABLE_CITY);
        db.close();
        return cnt;
    }
}