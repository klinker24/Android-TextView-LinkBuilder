package com.klinker.android.link_builder_example.list_view_example;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * This sample illustrates how to use LinkBuilder along with a ListView.OnItemClickListener method.
 *
 * It is pretty simple, but requires you to implement the LinkConsumableTextView rather than a normal
 * TextView in your layout.
 *
 * By doing this, the LinkConsumableTextView will only consume the touch event if the link was actually clicked.
 * If the link was not clicked, then it will defer to your ListView.OnItemClickListener method instead.
 *
 * The SampleAdapter contains the LinkBuilder code for the list items.
 */
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
