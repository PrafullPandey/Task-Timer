package com.vaio.p2.tasktimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by p2 on 16/7/17.
 *
 * Basic class to create Database
 *
 * only gonna used by {@link AppProvider}
 *
 */

public class AppDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="TaskTimer.db";
    public static final int DATABASE_VERSION =1;
    private static final String TAG = "AppDatabase";

//    Implement the AppDatabase as a singleton
    private static AppDatabase instance =null;

    private AppDatabase(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: contructor");

    }

    /**
     *
     * get the instace of app's singelton datatbase helper object
     *
     * @param context the content provider context
     *
     * @return a SQLite database Helper object
     *
     */
    static AppDatabase getInstance(Context context){
        if(instance == null){
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate: in");
        String sSQL;
        sSQL = "CREATE TABLE "+TaskContract.TABLE_NAME+" ("
                +TaskContract.Column._ID
                +" INTEGER PRIMARY KEY NOT NULL, "+TaskContract.Column.Name+" TEXT NOT NULL, "
                +TaskContract.Column.TASK_DESCRIPTOR+" TEXT, "
                +TaskContract.Column.SORTORDER
                +" INTEGER, CategoryID INTEGER);";

        sqLiteDatabase.execSQL(sSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade: start");
        switch (i){
            case 1:
//                upgrade logic from version 1
                break;
            default:
                throw new IllegalStateException("on upgrade() with unknown version "+i1);
        }
        Log.d(TAG, "onUpgrade: ends");

    }
}
