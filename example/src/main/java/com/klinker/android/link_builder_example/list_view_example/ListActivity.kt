package com.klinker.android.link_builder_example.list_view_example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

import com.klinker.android.link_builder_example.R

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
class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)

        val listView = findViewById<View>(R.id.list) as ListView
        listView.adapter = SampleAdapter(this)
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            Log.d(TAG, "onListItemClick position=$i")
        }
    }

    companion object {
        private val TAG = ListActivity::class.java.simpleName
    }
}
