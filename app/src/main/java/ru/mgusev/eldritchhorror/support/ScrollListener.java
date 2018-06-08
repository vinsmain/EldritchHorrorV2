package ru.mgusev.eldritchhorror.support;

import android.support.v7.widget.RecyclerView;

import ru.mgusev.eldritchhorror.adapter.MainAdapter;

public class ScrollListener extends RecyclerView.OnScrollListener {

    private MainAdapter adapter;

    public ScrollListener(MainAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
        adapter.closeSwipeLayout();
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        adapter.closeSwipeLayout();
    }
}
