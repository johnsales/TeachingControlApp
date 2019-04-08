package com.teachingcontrolapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperStudent extends SQLiteOpenHelper {

    public static final String BD_NAME = "students_db";
    public static final int BD_VERSION = 1;
    private static String SQL_CREATE = String.format(
            "CREATE TABLE if not exists %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "%s TEXT NOT NULL, %s DOUBLE NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)", StudentsContract.TABLE_NAME,
            StudentsContract.Columns._ID, StudentsContract.Columns.NAME,
            StudentsContract.Columns.GRADE, StudentsContract.Columns.COURSE, StudentsContract.Columns.USER_ID
    );

    private static String SQL_DROP = "DROP TABLE IF EXISTS " + StudentsContract.TABLE_NAME;

    private static DBHelperStudent instance;

    private DBHelperStudent(Context context) {

        super(context, BD_NAME, null, BD_VERSION);
    }

    public static DBHelperStudent getInstance(Context context){
        if(instance == null){
            instance = new DBHelperStudent(context);
            //context.deleteDatabase(BD_NAME);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}

