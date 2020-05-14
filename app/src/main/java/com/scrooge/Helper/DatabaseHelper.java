package com.scrooge.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.scrooge.Helper.DebtorContract.DebtorColumns;

import androidx.annotation.Nullable;



public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Debtor.db";
    private static final String SQL_CREATE_DEBTORS =
            "CREATE TABLE " + DebtorContract.DebtorColumns.TABLE_NAME + " (" +
                    DebtorColumns._ID + " INTEGER PRIMARY KEY," +
                    DebtorColumns.COLUMN_NAME_NAME + " TEXT," +
                    DebtorColumns.COLUMN_NAME_DEBT + " NUMERIC)";

    private static final String SQL_DELETE_DEBTORS =
            "DROP TABLE IF EXISTS " + DebtorColumns.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DEBTORS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DEBTORS);
        onCreate(db);
    }

}
