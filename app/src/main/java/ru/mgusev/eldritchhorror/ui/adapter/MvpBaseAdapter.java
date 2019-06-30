package ru.mgusev.eldritchhorror.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpDelegate;

public abstract class MvpBaseAdapter extends RecyclerView.Adapter {
    private MvpDelegate<? extends MvpBaseAdapter> mMvpDelegate;
    private MvpDelegate<?> mParentDelegate;
    private String mChildId;

    public MvpBaseAdapter(MvpDelegate<?> parentDelegate, String childId) {
        mParentDelegate = parentDelegate;
        mChildId = childId;

        getMvpDelegate().onCreate();
    }

    protected MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
            mMvpDelegate.setParentDelegate(mParentDelegate, mChildId);

        }
        return mMvpDelegate;
    }
}