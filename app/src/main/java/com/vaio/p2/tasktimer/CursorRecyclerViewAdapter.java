package com.vaio.p2.tasktimer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by p2 on 24/7/17.
 */

class CursorRecyclerViewAdapter extends RecyclerView.Adapter<CursorRecyclerViewAdapter.TaskViewHolder> {

    private static final String TAG = "CursorRecyclerViewAdapt";
    private Cursor mCursor;
    private onTaskClickListner mListner;

    interface onTaskClickListner {
        void oneditClick(Task task);

        void ondeleteClick(Task task);
    }


    public CursorRecyclerViewAdapter(Cursor mCursor, onTaskClickListner mListner) {
        Log.d(TAG, "CursorRecyclerViewAdapter: contructor called");
        this.mCursor = mCursor;
        this.mListner = mListner;
    }

    @Override
    public CursorRecyclerViewAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: starts");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CursorRecyclerViewAdapter.TaskViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: providing instruction");
        if (mCursor == null || mCursor.getCount() == 0) {
            holder.name.setText("Instruction");
            holder.description.setText(R.string.instruction);

            holder.editbutton.setVisibility(View.GONE);
            holder.deletebutton.setVisibility(View.GONE);
        } else {

            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("could'nt move curosr to position " + position);
            } else {

                final Task task = new Task(mCursor.getLong(mCursor.getColumnIndex(TaskContract.Column._ID)),
                        mCursor.getString(mCursor.getColumnIndex(TaskContract.Column.Name)),
                        mCursor.getString(mCursor.getColumnIndex(TaskContract.Column.TASK_DESCRIPTOR)),
                        mCursor.getInt(mCursor.getColumnIndex(TaskContract.Column.SORTORDER))
                );

                holder.name.setText(task.getmName());
                holder.description.setText(task.getmDescription());
                holder.editbutton.setVisibility(View.VISIBLE);
                holder.deletebutton.setVisibility(View.VISIBLE);

                View.OnClickListener blistner = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: starts");
                        switch (v.getId()){
                            case R.id.li_edit:
                                if(mListner!=null)
                                    mListner.oneditClick(task);
                                break;
                            case R.id.li_delete:
                                if(mListner!=null)
                                    mListner.ondeleteClick(task);
                                break;
                            default:
                                Log.d(TAG, "onClick: unexpected button clicked");
                                
                        }
                    }
                };

                /*class Listener implements View.OnClickListener {

                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: button clicked " + position);
                        Log.d(TAG, "onClick: task name is " + task.getmName());
                    }
                }
                Listener buttonListener = new Listener();*/
                holder.editbutton.setOnClickListener(blistner);
                holder.deletebutton.setOnClickListener(blistner);

            }
        }
        if (position % 3 == 0)
            holder.cardView.setCardBackgroundColor(Color.RED);
        else if (position % 3 == 1)
            holder.cardView.setCardBackgroundColor(Color.YELLOW);
        else if (position % 3 == 2)
            holder.cardView.setCardBackgroundColor(Color.BLUE);


    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: starts");
        return ((mCursor != null && mCursor.getCount() != 0) ? mCursor.getCount() : 1);
    }


    /**
     * Swap in a new cursor, returing the old cursor
     * The returned old cursor is <em>not</em> closed
     *
     * @param newCursor The new cursor to be used
     * @return Return the previously set cursor and null if none.
     * If the new cursor has the same instance of the old then null is returned.
     */

    Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor)
            return null;
        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
//            notify the observer about the new dataset
            notifyDataSetChanged();
        } else {
//            notify the observer about the lack of dataset
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;

    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "TaskViewHolder";

        TextView name = null;
        TextView description = null;
        ImageButton editbutton = null;
        ImageButton deletebutton = null;
        CardView cardView = null;

        public TaskViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "TaskViewHolder: starts");

            this.name = (TextView) itemView.findViewById(R.id.li_name);
            this.description = (TextView) itemView.findViewById(R.id.li_description);
            this.editbutton = (ImageButton) itemView.findViewById(R.id.li_edit);
            this.deletebutton = (ImageButton) itemView.findViewById(R.id.li_delete);
            this.cardView = (CardView) itemView.findViewById(R.id.cardView);

        }
    }
}
