/*
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.klinker.android.link_builder_example

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks

import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)

        val demoText = findViewById<View>(R.id.test_text) as TextView
        demoText.applyLinks(getLinks())
    }

    private fun getLinks(): List<Link> {
        val github = Link("TextView-LinkBuilder")
                .setTypeface(Typeface.DEFAULT_BOLD)
                .setOnClickListener { openLink(GITHUB_LINK) }

        val mentions = Link(Pattern.compile("@\\w{1,15}"))
                .setTextColor(Color.parseColor("#00BCD4"))
                .setHighlightAlpha(.4f)
                .setOnClickListener { clickedText ->
                    openLink(TWITTER_PROFILE + clickedText.replace("@", ""))
                }

        val numbers = Link(Pattern.compile("[0-9]+"))
                .setTextColor(Color.parseColor("#FF9800"))
                .setOnClickListener { showToast("Clicked: $it") }

        val longClickHere = Link("here")
                .setTextColor(Color.parseColor("#259B24"))
                .setOnLongClickListener { showToast("You long clicked. Nice job.") }

        val yes = Link("Yes").setUnderlined(true)
                .setTextColor(Color.parseColor("#FFEB3B"))

        val no = Link("No").setUnderlined(false)
                .setTextColor(Color.parseColor("#FFEB3B"))

        val bold = Link("bold").setBold(true)
                .setTextColor(Color.parseColor("#FF0000"))

        val prepend = Link("prepended").setPrependedText("(!)")
        val appended = Link("appended").setAppendedText("(!)")

        val playStore = Link("Play Store")
                .setTextColor(Color.parseColor("#FF9800"))
                .setTextColorOfHighlightedLink(Color.parseColor("#FF6600"))
                .setHighlightAlpha(0f)
                .setOnClickListener { openLink(PLAY_STORE) }

        return listOf(github, mentions, numbers, longClickHere, yes, no, bold, prepend, appended, playStore)
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    private fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val GITHUB_LINK = "https://github.com/klinker24"
        private const val TWITTER_PROFILE = "https://twitter.com/"
        private const val PLAY_STORE = "https://play.google.com/store/apps/developer?id=Klinker+Apps&hl=en"
    }
}
