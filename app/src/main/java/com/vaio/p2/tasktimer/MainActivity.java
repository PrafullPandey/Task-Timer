package com.vaio.p2.tasktimer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starts");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String projection[] = {TaskContract.Column.Name , TaskContract.Column.TASK_DESCRIPTOR };
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(TaskContract.CONTENT_URI,projection,null,null,TaskContract.Column.SORTORDER);

        if(cursor!=null){
            Log.d(TAG, "onCreate: no of rows is "+cursor.getCount());
            while(cursor.moveToNext()){
                for(int i=0;i<cursor.getCount();i++){
                    Log.d(TAG, "onCreate: "+cursor.getColumnName(i)+" : "+cursor.getString(i));
                }
                Log.d(TAG, "onCreate: ***************************");
            }
            cursor.close();
        }
        Log.d(TAG, "onCreate: ends");

//        AppDatabase appDatabase = AppDatabase.getInstance(this);
//        final SQLiteDatabase sqLiteDatabase = appDatabase.getReadableDatabase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
