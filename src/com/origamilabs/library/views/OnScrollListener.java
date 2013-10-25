package com.origamilabs.library.views;

public interface OnScrollListener {

    public static final int SCROLL_STATE_FLING = 2;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

    public void onScroll(StaggeredGridView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);

    public void onScrollStateChanged(StaggeredGridView view, int scrollState);
}
