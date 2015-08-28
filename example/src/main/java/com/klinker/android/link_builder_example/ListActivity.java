package com.klinker.android.link_builder_example;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class ListActivity extends android.app.ListActivity {
    private static final String TAG = ListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new SampleAdapter(this));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, "onListItemClick position=" + position);
    }
}
