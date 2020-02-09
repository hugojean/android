package com.example.a21602196.tp5.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MesAnnonces.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_PRIX + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DESC + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_PSEUDO + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_MAIL + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TEL + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_VILLE + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_CP + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
