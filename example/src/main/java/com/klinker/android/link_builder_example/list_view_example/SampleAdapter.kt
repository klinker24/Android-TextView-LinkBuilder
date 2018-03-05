package com.klinker.android.link_builder_example.list_view_example

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.LinkBuilder
import com.klinker.android.link_builder.LinkConsumableTextView
import com.klinker.android.link_builder.applyLinks
import com.klinker.android.link_builder_example.R

class SampleAdapter(private val mContext: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return SIZE
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false)
        }

        val textView = convertView as LinkConsumableTextView?
        textView!!.text = String.format(TEXT, position)

        // Add 2 Links
        val link1 = Link(LINK1).setOnClickListener(object : Link.OnClickListener {
            override fun onClick(clickedText: String) {
                Log.d(TAG, LINK1)
            }
        })

        val link2 = Link(LINK2).setOnClickListener(object : Link.OnClickListener {
            override fun onClick(clickedText: String) {
                Log.d(TAG, LINK2)
            }
        })

        textView.applyLinks(link1, link2)
        return convertView
    }

    companion object {

        private val TAG = SampleAdapter::class.java.simpleName
        private const val SIZE = 42
        private const val LINK1 = "First link"
        private const val LINK2 = "Second link"
        private const val TEXT = "This is item %d.  $LINK1  $LINK2"
    }
}
