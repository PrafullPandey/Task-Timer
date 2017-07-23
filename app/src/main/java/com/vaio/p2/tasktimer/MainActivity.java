package com.vaio.p2.tasktimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

//    will tell whether the activity is in two pane mode
//    i.e running in landscape mode on tablet
    private boolean mTwoPane =false;
    public static final String ADD_EDIT_FRAGMENT = "AddEditFragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starts");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);













/*
        String projection[] = {TaskContract.Column._ID,
                TaskContract.Column.Name,
                TaskContract.Column.TASK_DESCRIPTOR,
                TaskContract.Column.SORTORDER};

        ContentResolver contentResolver = getContentResolver();

        ContentValues values = new ContentValues();

        values.put(TaskContract.Column.Name , "Android");
        values.put(TaskContract.Column.TASK_DESCRIPTOR, "Android N course");
        values.put(TaskContract.Column.SORTORDER, 1);
        Uri uri = contentResolver.insert(TaskContract.CONTENT_URI,values);


       values.put(TaskContract.Column.Name , "M-L");
        values.put(TaskContract.Column.TASK_DESCRIPTOR, "ML A-Z course");

        int count  = contentResolver.update(TaskContract.buildTaskUri(2),values,null,null);
        Log.d(TAG, "onCreate: "+count+" records updated");



        values.put(TaskContract.Column.TASK_DESCRIPTOR, "Android SQLite Test");
        values.put(TaskContract.Column.SORTORDER, 20);
        String selection = TaskContract.Column.SORTORDER+" = "+1;
        int count  = contentResolver.update(TaskContract.CONTENT_URI,values,selection,null);
        Log.d(TAG, "onCreate: "+count+" records updated");



        values.put(TaskContract.Column.TASK_DESCRIPTOR, "Android SQLite Test for deletion");
        String selection = TaskContract.Column.SORTORDER+" = ?";
        String args[] = {"20"};
        int count  = contentResolver.update(TaskContract.CONTENT_URI,values,selection,args);
        Log.d(TAG, "onCreate: "+count+" records updated");

        int count = contentResolver.delete(TaskContract.buildTaskUri(2),null,null);




        Cursor cursor = contentResolver.query(TaskContract.CONTENT_URI,
                projection,
                null,
                null,
                TaskContract.Column.SORTORDER);

        if (cursor != null) {
            Log.d(TAG, "onCreate: no of rows is " + cursor.getCount());
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + " : " + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: ***************************");
            }
            cursor.close();
        }
        Log.d(TAG, "onCreate: ends");

//        AppDatabase appDatabase = AppDatabase.getInstance(this);
//        final SQLiteDatabase sqLiteDatabase = appDatabase.getReadableDatabase();
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.menumain_addTask:
                taskEditRequest(null);
                break;
            case R.id.menumain_generate:
                break;
            case R.id.menumain_settings:
                break;
            case R.id.menumain_showabout:
                break;
            case R.id.menumain_showDuration:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void taskEditRequest(Task task){
        Log.d(TAG, "taskEditRequest: starts");
        if(mTwoPane){
            Log.d(TAG, "taskEditRequest: in landscape mode");
        }else{
            Log.d(TAG, "taskEditRequest: in single pane mode");
//            in single pane mode , start the detail activiyt for selected item Id.
            Intent detailintent = new Intent(MainActivity.this , AddEditActivity.class);
            if(task!=null){
//                editing a task
                detailintent.putExtra(Task.class.getSimpleName() , task);
                startActivity(detailintent);
            }else{
//                adding a new taskk
                startActivity(detailintent);
            }
        }
    }
}
