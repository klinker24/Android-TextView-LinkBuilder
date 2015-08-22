package com.klinker.android.link_builder_example;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

public class SampleAdapter extends BaseAdapter {
    private static final String TAG = SampleAdapter.class.getSimpleName();
    private static final int SIZE = 42;
    private static final String LINK1 = "First link";
    private static final String LINK2 = "Second link";
    private static final String TEXT = "This is item %d.  " + LINK1 + "  " + LINK2;

    private final Context mContext;

    public SampleAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return SIZE;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(String.format(TEXT, position));

        // Add 2 Links
        Link link1 = new Link(LINK1).setOnClickListener(new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                Log.d(TAG, LINK1);
            }
        });
        Link link2 = new Link(LINK2).setOnClickListener(new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                Log.d(TAG, LINK2);
            }
        });
        LinkBuilder.on(textView)
                .addLink(link1)
                .addLink(link2)
                .build();

        return convertView;
    }
}
