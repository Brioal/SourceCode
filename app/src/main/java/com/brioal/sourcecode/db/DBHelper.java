package com.brioal.sourcecode.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/3.
 */

public class DBHelper extends SQLiteOpenHelper {
    private final String API_HISTORY_SQL = "CREATE TABLE history ( mKey text primary key , mTime long )";
    private final String LIB_HISTORY_SQL = "CREATE TABLE libhistory ( mKey text primary key , mTime long )";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(API_HISTORY_SQL);
        db.execSQL(LIB_HISTORY_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
