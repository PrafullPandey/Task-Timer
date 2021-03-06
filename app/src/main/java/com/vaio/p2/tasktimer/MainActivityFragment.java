package com.vaio.p2.tasktimer;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.InvalidParameterException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainActivityFragment";
    public static final int LOADER_ID = 0;
    private CursorRecyclerViewAdapter mCursorAdapter; //add adapter reference
    private TextView tv =null;



    public MainActivityFragment() {
        Log.d(TAG, "MainActivityFragment: starts");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_main,container , false);
        tv =(TextView)view.findViewById(R.id.no_task);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCursorAdapter = new CursorRecyclerViewAdapter(null,(CursorRecyclerViewAdapter.onTaskClickListner)getActivity());
        recyclerView.setAdapter(mCursorAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts");
        super.onActivityCreated(savedInstanceState);

//        loader is best initialised in this method
        getLoaderManager().initLoader(LOADER_ID,null,this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: starts with id "+id );

        String projection[] = {TaskContract.Column._ID,
                TaskContract.Column.Name,
                TaskContract.Column.TASK_DESCRIPTOR,
                TaskContract.Column.SORTORDER};
//      <order by> Task.SortOrder ,Task.Name COLLATE NOCASE
        String sortOrder = TaskContract.Column.SORTORDER+" , "+TaskContract.Column.Name+ " COLLATE NOCASE";

        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(), TaskContract.CONTENT_URI, projection, null, null, sortOrder);

            default:
                throw new InvalidParameterException(TAG + " on create loader called with invalid id " + id);

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: entering load finished");
        if(data.getCount()!=0)
            tv.setVisibility(View.GONE);
        mCursorAdapter.swapCursor(data);
        int count = mCursorAdapter.getItemCount();
/*
        if(data!=null){
            while(data.moveToNext()){
                for(int i= 0;i<data.getColumnCount();i++){
                    Log.d(TAG, "onLoadFinished: "+data.getColumnName(i)+" : "+data.getString(i));
                }
                Log.d(TAG, "onLoadFinished: =====================");
            }
            count = data.getCount();
            Log.d(TAG, "onLoadFinished: count is "+count);

        }
*/

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        mCursorAdapter.swapCursor(null);
    }
}
