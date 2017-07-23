package com.vaio.p2.tasktimer;

import java.io.Serializable;

/**
 * Created by p2 on 17/7/17.
 */

class Task implements Serializable {
    public static final long serialVersionUID = 20170720L;

    private long mId;
    private final String mName;
    private final String mDescription;
    private final int mSortOrder;

    public Task(long mId, String mName, String mDescription, int mSortOrder) {
        this.mId = mId;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mSortOrder = mSortOrder;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public long getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public int getmSortOrder() {
        return mSortOrder;
    }

    @Override
    public String toString() {
        return "Task{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mSortOrder=" + mSortOrder +
                '}';
    }
}
