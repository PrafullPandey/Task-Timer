package com.vaio.p2.tasktimer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by p2 on 16/7/17.
 *
 * provider for the TASK Timer app. This is the only that knows about {@link AppDatabase} .
 *
 */

public class AppProvider extends ContentProvider {

    private static final String TAG = "AppProvider";

    private AppDatabase mOpenHelper;
    public static final UriMatcher sUriMatcher = buildUriMacther();



    static  final String CONTENT_AUTHORITY = "com.vaio.p2.tasktimer.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    private static final int TASK = 100;
    private static final int TASK_ID = 101;

    private static final int TIMMINGS = 200;
    private static final int TIMMINGS_ID = 201;

/*    public static final int TASK_TIMMINGS = 300;
    public static final int TAKS_TIMMINGS_ID = 301;
    */

    private static final int TASK_DURATION = 400;
    private static final int TASK_DURATION_ID=401;


    private static UriMatcher buildUriMacther(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

//        eg. content://com.vaio.p2.tasktimer.provider/task
        matcher.addURI(CONTENT_AUTHORITY,TaskContract.TABLE_NAME, TASK);
//        eg. content://com.vaio.p2.tasktimer.provider/task/8
        matcher.addURI(CONTENT_AUTHORITY,TaskContract.TABLE_NAME+"/#",TASK_ID);

/*
        matcher.addURI(CONTENT_AUTHORITY,TimmingsContract.TABLE_NAME,TIMMINGS);
        matcher.addURI(CONTENT_AUTHORITY,TimmingsContract.TABLE_NAME+"/#",TIMMINGS_ID);

        matcher.addURI(CONTENT_AUTHORITY,DurationContract.TABLE_NAME,TASK_DURATION);
        matcher.addURI(CONTENT_AUTHORITY,DurationContract.TABLE_NAME+"/#",TASK_DURATION_ID);
        */

        return matcher;

    }



    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortorder) {
        Log.d(TAG, "query: passed with uri "+uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query: match is "+match);

        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        switch(match){
            case TASK:
                sQLiteQueryBuilder.setTables(TaskContract.TABLE_NAME);
                break ;
            case TASK_ID:
                sQLiteQueryBuilder.setTables(TaskContract.TABLE_NAME);
                long taskid = TaskContract.getTaskid(uri);
                sQLiteQueryBuilder.appendWhere(TaskContract.Column._ID+" = "+taskid);
                break;
/*
            case TIMMINGS:
                sQLiteQueryBuilder.setTables(TimmingsContract.TABLE_NAME);
                break ;

            case TIMMINGS_ID:
                sQLiteQueryBuilder.setTables(TimmingsContract.TABLE_NAME);
                long timmingid = TaskContract.getTimmingid(uri);
                sQLiteQueryBuilder.appendWhere(TimmingsContract.Column._ID+" = "+timmingid);
                break;

            case TASK_DURATION:
                sQLiteQueryBuilder.setTables(DurationContract.TABLE_NAME);
                break ;

            case TASK_DURATION_ID:
                sQLiteQueryBuilder.setTables(DurationContract.TABLE_NAME);
                long durationid = TaskContract.getDurationid(uri);
                sQLiteQueryBuilder.appendWhere(DurationContract.Column._ID+" = "+durationid);
                break;
            */
            default:
                throw new IllegalArgumentException("Unknown Uri "+ uri );

        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = sQLiteQueryBuilder.query(db,projection,selection,selectionArgs,null,null,sortorder);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
