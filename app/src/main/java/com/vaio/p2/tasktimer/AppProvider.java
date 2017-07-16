package com.vaio.p2.tasktimer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
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
        final int match = sUriMatcher.match(uri);

        switch(match){
            case TASK:
                return TaskContract.CONTENT_TYPE;

            case TASK_ID:
                return TaskContract.CONTENT_ITEM_TYPE;
/*
            case TIMMINGS:
                return TimmingsContract.CONTENT_TYPE;

            case TIMMINGS_ID:
                return TimmingsContract.CONTENT_ITEM_TYPE;


            case TASK_DURATION:
                return DurationContract.CONTENT_TYPE;

            case TASK_DURATION_ID:
                return DurationContract.CONTENT_ITEM_TYPE;
            */
            default:
                throw new IllegalArgumentException("Unknown Uri "+ uri );

        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "insert: Entering insert called with uri "+uri);

        final int match = sUriMatcher.match(uri);

        final SQLiteDatabase db;

        Uri resultUri;
        long recordId;

        switch (match){
            case TASK:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(TaskContract.TABLE_NAME,null,contentValues);
                if(recordId>=0){
                    resultUri=TaskContract.buildTaskUri(recordId);

                }else{
                    throw new android.database.SQLException("failed to insert "+uri.toString());
                }

                break;

/*
            case TIMMINGS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(TimmingsContract.Timmings.buildTimmingUri(recordId));
                if(recordId>=0){
                    resultUri = TimmingsContract.Timmings.buildTimmingUri(recordId);
                }else{
                    throw new android.database.SQLException("failed to insert "+uri.toString());
                }
                break;
*/

            default:
                throw new IllegalArgumentException("unknown : "+uri);

        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        Log.d(TAG, "delete: uri is "+uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "delete: match is "+match);

        final SQLiteDatabase db ;

        String selectionCriteria;
        int count;

        switch (match){
            case TASK:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(TaskContract.TABLE_NAME ,s ,strings);
                break ;

            case TASK_ID:
                db = mOpenHelper.getWritableDatabase();
                long taskId=TaskContract.getTaskid(uri);
                selectionCriteria = TaskContract.Column._ID+" = "+taskId;
                if(s!=null && s.length()>=0){
                    selectionCriteria+=" AND ("+s+")";
                }
                count = db.delete(TaskContract.TABLE_NAME ,selectionCriteria ,strings);
                break ;
/*

            case TIMMINGS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(TimmingsContract.TABLE_NAME  ,s ,strings);
                break ;

            case TIMMINGS_ID:
                db = mOpenHelper.getWritableDatabase();
                long taskId=TimmingsContract.getTaskid(uri);
                selectionCriteria = TaskContract.Column._ID+" = "+taskId;
                if(selectionCriteria!=null && selectionCriteria.length()>=0){
                    selectionCriteria+=" AND ("+s+")";
                }
                count = db.delete(TimmingsContract.TABLE_NAME ,selectionCriteria ,strings);
                break ;
*/

            default:
                throw new IllegalArgumentException("unknown uri "+uri);

        }
        Log.d(TAG, "delete: returning "+count);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "update: uri is "+uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "update: match is "+match);

        final SQLiteDatabase db ;

        String selectionCriteria;
        int count;

        switch (match){
            case TASK:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(TaskContract.TABLE_NAME ,contentValues ,s ,strings);
                break ;

            case TASK_ID:
                db = mOpenHelper.getWritableDatabase();
                long taskId=TaskContract.getTaskid(uri);
                selectionCriteria = TaskContract.Column._ID+" = "+taskId;
                if(s!=null && s.length()>=0){
                    selectionCriteria= selectionCriteria +" AND ( "+s+" )";
                }
                count = db.update(TaskContract.TABLE_NAME ,contentValues ,selectionCriteria ,strings);
                break ;
/*

            case TIMMINGS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(TimmingsContract.TABLE_NAME ,contentValues ,s ,strings);
                break ;

            case TIMMINGS_ID:
                db = mOpenHelper.getWritableDatabase();
                long taskId=TimmingsContract.getTaskid(uri);
                selectionCriteria = TaskContract.Column._ID+" = "+taskId;
                if(selectionCriteria!=null && selectionCriteria.length()>=0){
                    selectionCriteria+=" AND ("+s+")";
                }
                count = db.update(TimmingsContract.TABLE_NAME ,contentValues ,selectionCriteria ,strings);
                break ;
*/

            default:
                throw new IllegalArgumentException("unknown uri "+uri);

        }
        Log.d(TAG, "update: returning "+count);
        return count;
    }
}
