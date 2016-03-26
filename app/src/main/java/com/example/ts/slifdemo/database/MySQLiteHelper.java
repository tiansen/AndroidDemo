package com.example.ts.slifdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TS on 2016/3/23.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String CREATE_NOTE_TABLE_SQL = "create table note(" +
            "_id integer primary key autoincrement," +
            "title text NOT NULL DEFAULT ''," +
            "content text NOT NULL DEFAULT ''," +
            "flag int NOT NULL DEFAULT 1," +
            "create_date datetime," +
            "name text)";
    public MySQLiteHelper(Context context) {
        super(context, "notes.db", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table note");
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE_SQL);

    }
}
