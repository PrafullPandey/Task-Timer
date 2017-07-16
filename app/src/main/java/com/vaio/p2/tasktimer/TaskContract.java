package com.vaio.p2.tasktimer;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by p2 on 16/7/17.
 */

public class TaskContract {
    static final String TABLE_NAME = "Tasks";

    //Tasks Feilds

    /**
     *The Uri to acess the Task Table
     *
     */

    public static final Uri CONTENT_URI = Uri.withAppendedPath(AppProvider.CONTENT_AUTHORITY_URI,TABLE_NAME);
    static final String CONTENT_TYPE= "vnd.android.cursor.dir/vnd."+AppProvider.CONTENT_AUTHORITY+"."+TABLE_NAME;
    static final String CONTENT_ITEM_TYPE= "vnd.android.cursor.cursor/vnd."+AppProvider.CONTENT_AUTHORITY+"."+TABLE_NAME;

    static Uri buildTaskUri(long taskid){
       return ContentUris.withAppendedId(CONTENT_URI,taskid);
    }

    static long getTaskid(Uri uri){
        return ContentUris.parseId(uri);
    }

    public static class Column{
        public static final String _ID = BaseColumns._ID;
        public static final String Name = "Name";
        public static final String TASK_DESCRIPTOR ="Description";
        public static final String SORTORDER ="SortOrder";

        private Column(){
//            to prevent creation of instances of this class
        }
    }
}
