package com.klinker.android.link_builder_example

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.LinkBuilder

import java.util.ArrayList
import java.util.regex.Pattern

class HtmlLinkExampleActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_html_example)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)


        val demo1 = findViewById<View>(R.id.demo1) as TextView
        demo1.text = Html.fromHtml(TEXT)
        LinkBuilder.on(demo1).addLinks(getLinks()).build()

        val demo2 = findViewById<View>(R.id.demo2) as TextView
        demo2.text = Html.fromHtml(TEXT.replace("</?a[^>]*>".toRegex(), ""))
        LinkBuilder.on(demo2).addLinks(getLinks()).build()
    }

    private fun getLinks(): List<Link> {
        val google = Link("www.google.com")
        google.setTextColor(Color.parseColor("#00BCD4"))
        google.setHighlightAlpha(.4f)
        google.setOnClickListener(object : Link.OnClickListener {
            override fun onClick(clickedText: String) {
                showToast("clicked: $clickedText")
            }
        })

        val exampleText = Link("this")
        exampleText.setTextColor(Color.parseColor("#00BCD4"))
        exampleText.setHighlightAlpha(.4f)
        exampleText.setOnClickListener(object : Link.OnClickListener {
            override fun onClick(clickedText: String) {
                showToast("clicked the example text")
            }
        })

        return listOf(google, exampleText)
    }

    private fun showToast(text: String) {
        Toast.makeText(this@HtmlLinkExampleActivity, text, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val TEXT = "Here is an example link <a href=\"www.google.com\">www.google.com</a>." + "To show it alongside other LinkBuilder functionality, lets highlight this."
    }
}
