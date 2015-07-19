package com.example.tuananh.weatherforecast.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TuanAnh on 06/07/2015.
 */
public class MyDatabase extends SQLiteOpenHelper {
    //name of database
    public static final String DATABASE = "WEATHER";
    public static final String CURRENTTABLE = "CURRENT";
    public static final String WEEKTABLE = "WEEK";
    public static final String ID = "ID";
    public static final String COUNTRY = "COUNTRY";
    public static final String DATE = "DATE";
    public static final String TEAMP_C = "TEAMP_C";
    public static final String TEAMP_F = "TEAMP_F";
    public static final String WIND = "WIND";
    public static final String PRESSURE = "PRESSURE";
    public static final String HUMMID = "HUMMID";
    public static final String STATE = "STATE";
    public static final String ICON = "ICON";
    public static final String DAY = "DAY";
    public static final String MONTH = "MONTH";
    public static final String YEAR = "YEAR";
    public static final String HOUR = "HOUR";
    public MyDatabase(Context context) {
        super(context, DATABASE, null, 1);
    }

    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

/*    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                + CURRENTTABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COUNTRY + " TEXT, "
                + HOUR + " TEXT, "
                + DATE + " TEXT, "
                + DAY + " LONG, "
                + MONTH + " TEXT, "
                + YEAR + " LONG, "
                + STATE + " TEXT, "
                + TEAMP_C + " INTEGER, "
                + TEAMP_F + " INTEGER, "
                + WIND + " INTEGER, "
                + PRESSURE + " TEXT, "
                + HUMMID + " TEXT, "
                + ICON + " TEXT);");
        db.execSQL("CREATE TABLE "
                + WEEKTABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT, "
                + STATE + " TEXT, "
                + TEAMP_C + " TEXT, "
                + TEAMP_F + " TEXT, "
                + ICON + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
