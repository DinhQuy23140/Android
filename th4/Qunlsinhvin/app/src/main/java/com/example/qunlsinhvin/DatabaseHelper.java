package com.example.qunlsinhvin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_lophoc";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "tb_lophoc";
    private static final String COLUMN_MA = "malop";
    private static final String COLUMN_TENLOP ="tenlop";
    private static final String COLUMN_SISO = "siso";
    //private static final String CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME +" (" +
            //COLUMN_MA +"PRIMARY KEY TEXT, " + COLUMN_TENLOP + " TEXT, " + COLUMN_SISO + " TEXT,"

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
