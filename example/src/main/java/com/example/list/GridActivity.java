package com.example.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.origamilabs.library.views.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridActivity extends ActionBarActivity {
    static class Item {
        public long mId;
        public CharSequence mText;
        private final int mHeight;

        public Item(long id, String text, int height) {
            mId = id;
            mText = text;
            mHeight = height;
        }
    }

    private static final int LOADER_MAIN = 0;

    private MyAdapter mAdapter;
    private LoaderManager.LoaderCallbacks<List<Item>> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<Item>>() {
        @Override
        public Loader<List<Item>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<List<Item>>(GridActivity.this) {
                @Override
                public List<Item> loadInBackground() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                    final Random random = new Random();
                    final ArrayList<Item> items = new ArrayList<Item>();
                    for (int i = 0; i < 100; ++i) {
                        final int height = random.nextInt() % 400 + 200;
                        items.add(new Item(i, String.format("Item: %d", i), height));
                    }
                    return items;
                }

                @Override
                protected void onStartLoading() {
                    forceLoad();
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Item>> loader, List<Item> data) {
            mAdapter.swapData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Item>> loader) {
            mAdapter.swapData(null);

        }
    };

    public static class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<Item> mData;

        public MyAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public Item getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).mId;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            }
            final Item item = getItem(position);
            final TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(item.mText);
            textView.setMinHeight(item.mHeight);
            return view;
        }

        public void swapData(List<Item> data) {
            mData = data;
            if (data == null) {
                notifyDataSetInvalidated();
            } else {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_activity);

        final StaggeredGridView listView = (StaggeredGridView) findViewById(android.R.id.list);

        mAdapter = new MyAdapter(this);
        listView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(LOADER_MAIN, null, mLoaderCallbacks);
    }

}
