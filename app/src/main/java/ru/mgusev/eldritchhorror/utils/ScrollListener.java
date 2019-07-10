package ru.mgusev.eldritchhorror.utils;

import androidx.recyclerview.widget.RecyclerView;

import ru.mgusev.eldritchhorror.ui.adapter.main.MainAdapter;

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
