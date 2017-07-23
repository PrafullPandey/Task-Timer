package com.vaio.p2.tasktimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";

    public enum FragmentEditMode {EDIT , ADD}

    private FragmentEditMode mMode ;

    private EditText mNameTextView;
    private EditText mDescriptionTextView ;
    private EditText mSortOrderTextView ;
    private Button mSave ;



    public AddEditActivityFragment() {
        Log.d(TAG, "AddEditActivityFragment: constructor called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");

        View view = inflater.inflate(R.layout.fragment_add_edit , container ,false);

        mNameTextView = (EditText)view.findViewById(R.id.addedit_name);
        mDescriptionTextView = (EditText)view.findViewById(R.id.addedit_description);
        mSortOrderTextView = (EditText)view.findViewById(R.id.addedit_sortorder);
        mSave = (Button)view.findViewById(R.id.addedit_save);

        Bundle arguments = getActivity().getIntent().getExtras();   // we would be changing it later

        final Task task ;
        if(arguments!=null){
            Log.d(TAG, "onCreateView: getting task details");
            task = (Task) arguments.getSerializable(Task.class.getSimpleName());
            if(task!=null){
                Log.d(TAG, "onCreateView: Task details found , editing ...");
                mNameTextView.setText(task.getmName());
                mDescriptionTextView.setText(task.getmDescription());
                mSortOrderTextView.setText(Integer.toString(task.getmSortOrder()));
                mMode = FragmentEditMode.EDIT;
            }else{
//                no task found so we must be adding a new task
                mMode =FragmentEditMode.ADD;
            }
        }else{
            task =null;
            Log.d(TAG, "onCreateView: no arguments");
            mMode =FragmentEditMode.ADD;
        }
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                update the database if atleast one entry is updated
//                no need to hit the databse in case of no change
                int so;  // to save the repeated conversions to int
                if(mSortOrderTextView.length()>0){
                    so = Integer.parseInt(mSortOrderTextView.getText().toString());
                }else{
                    so=0;
                }
                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();

                switch (mMode){
                    case EDIT:
                        if(!mNameTextView.getText().toString().equals(task.getmName())){
                            values.put(TaskContract.Column.Name , mNameTextView.getText().toString());
                        }
                        if (!mDescriptionTextView.getText().toString().equals(task.getmDescription())){
                            values.put(TaskContract.Column.TASK_DESCRIPTOR , mDescriptionTextView.getText().toString());
                        }
                        if(so != task.getmSortOrder()){
                            values.put(TaskContract.Column.SORTORDER,so);
                        }
                        if(values.size()!=0){
                            Log.d(TAG, "onClick: updating task");
                            contentResolver.update(TaskContract.buildTaskUri(task.getmId()),values,null,null);
                        }
                        break ;
                    case ADD:
                        if(mNameTextView.length()!=0){
                            values.put(TaskContract.Column.Name , mNameTextView.getText().toString());
                            values.put(TaskContract.Column.TASK_DESCRIPTOR , mDescriptionTextView.getText().toString());
                            values.put(TaskContract.Column.SORTORDER,so);
                            Log.d(TAG, "onClick: adding task");

                            contentResolver.insert(TaskContract.CONTENT_URI,values);
                        }
                        break;
                    

                }
                Log.d(TAG, "onClick: done editing");
            }
        });
        Log.d(TAG, "onCreateView: exiting...");
        return view;
    }
}
