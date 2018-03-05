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

package com.klinker.android.link_builder

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.widget.TextView

import java.util.ArrayList
import java.util.regex.Pattern


class LinkBuilder {

    private var context: Context? = null

    private val type: Int
    private val links = ArrayList<Link>()

    private var textView: TextView? = null
    private var text: CharSequence? = null
    private var findOnlyFirstMatch = false
    private var spannable: SpannableString? = null

    /**
     * Construct a LinkBuilder object.
     *
     * @param type TYPE_TEXT or TYPE_TEXT_VIEW
     */
    private constructor(type: Int) {
        this.type = type
    }

    @Deprecated("")
    constructor(textView: TextView?) {
        if (textView == null) {
            throw IllegalArgumentException("textView is null")
        }

        this.textView = textView
        this.type = -1

        setText(textView.text.toString())
    }

    fun setTextView(textView: TextView): LinkBuilder {
        this.textView = textView
        return setText(textView.text)
    }

    fun setText(text: CharSequence): LinkBuilder {
        this.text = text
        return this
    }

    fun setContext(context: Context): LinkBuilder {
        this.context = context
        return this
    }

    fun setFindOnlyFirstMatchesForAnyLink(findOnlyFirst: Boolean): LinkBuilder {
        this.findOnlyFirstMatch = findOnlyFirst
        return this
    }

    /**
     * Add a single link to the builder.
     *
     * @param link the rule that you want to link with.
     */
    fun addLink(link: Link): LinkBuilder {
        this.links.add(link)
        return this
    }

    /**
     * Add a list of links to the builder.
     *
     * @param links list of rules you want to link with.
     */
    fun addLinks(links: List<Link>): LinkBuilder {
        if (links.isEmpty()) {
            throw IllegalArgumentException("link list is empty")
        }

        this.links.addAll(links)
        return this
    }

    /**
     * Execute the rules to create the linked text.
     */
    fun build(): CharSequence? {
        // we extract individual links from the patterns
        turnPatternsToLinks()

        // exit if there are no links
        if (links.size == 0) {
            return null
        }

        // we need to apply this text before the links are created
        applyAppendedAndPrependedText()


        // add those links to our spannable text so they can be clicked
        for (link in links) {
            addLinkToSpan(link)
        }

        if (type == TYPE_TEXT_VIEW) {
            // set the spannable text
            textView!!.text = spannable

            // add the movement method so we know what actions to perform on the clicks
            addLinkMovementMethod()
        }

        return spannable
    }

    /**
     * Add the link rule and check if spannable text is created.
     *
     * @param link rule to add to the text.
     */
    private fun addLinkToSpan(link: Link) {
        // create a new spannable string if none exists
        if (spannable == null) {
            spannable = SpannableString.valueOf(text)
        }

        // add the rule to the spannable string
        addLinkToSpan(spannable!!, link)
    }

    /**
     * Find the link within the spannable text
     *
     * @param s    spannable text that we are adding the rule to.
     * @param link rule to add to the text.
     */
    private fun addLinkToSpan(s: Spannable, link: Link) {
        // get the current text
        val pattern = Pattern.compile(Pattern.quote(link.text))
        val matcher = pattern.matcher(text!!)

        // find one or more links inside the text
        while (matcher.find()) {

            // find the start and end point of the linked text within the TextView
            val start = matcher.start()

            //int start = text.indexOf(link.getText());
            if (start >= 0 && link.text != null) {
                val end = start + link.text!!.length

                // add link to the spannable text
                applyLink(link, Range(start, end), s)
            }

            // if we are only looking for the first occurrence of this pattern,
            // then quit now and don't look any further
            if (findOnlyFirstMatch) {
                break
            }
        }
    }

    /**
     * Add the movement method to handle the clicks.
     */
    private fun addLinkMovementMethod() {
        if (textView == null) {
            return
        }

        val m = textView!!.movementMethod
        if (m == null || m !is TouchableMovementMethod) {
            if (textView!!.linksClickable) {
                textView!!.movementMethod = TouchableMovementMethod.instance
            }
        }
    }

    /**
     * Set the link rule to the spannable text.
     *
     * @param link  rule we are applying.
     * @param range the start and end point of the link within the text.
     * @param text  the spannable text to add the link to.
     */
    private fun applyLink(link: Link, range: Range, text: Spannable) {
        val existingSpans = text.getSpans(range.start, range.end, TouchableSpan::class.java)
        if (existingSpans.isEmpty()) {
            val span = TouchableSpan(context!!, link)
            text.setSpan(span, range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else {
            var newSpanConsumesAllOld = true
            for (span in existingSpans) {
                val start = spannable!!.getSpanStart(span)
                val end = spannable!!.getSpanEnd(span)
                if (range.start > start || range.end < end) {
                    newSpanConsumesAllOld = false
                    break
                } else {
                    text.removeSpan(span)
                }
            }

            if (newSpanConsumesAllOld) {
                val span = TouchableSpan(context!!, link)
                text.setSpan(span, range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    /**
     * Find the links that contain patterns and convert them to individual links.
     */
    private fun turnPatternsToLinks() {
        var size = links.size
        var i = 0

        while (i < size) {
            if (links[i].pattern != null) {
                addLinksFromPattern(links[i])

                links.removeAt(i)
                size--
            } else {
                i++
            }
        }
    }

    /**
     * Add the appended and prepended text to the links and apply it to the TextView
     * so that we can create the SpannableString.
     */
    private fun applyAppendedAndPrependedText() {
        for (i in links.indices) {
            val link = links[i]

            if (link.prependedText != null) {
                val totalText = link.prependedText + " " + link.text

                text = TextUtils.replace(text, arrayOf(link.text), arrayOf(totalText))
                links[i].setText(totalText)
            }

            if (link.appendedText != null) {
                val totalText = link.text + " " + link.text

                text = TextUtils.replace(text, arrayOf(link.text), arrayOf(totalText))
                links[i].setText(totalText)
            }
        }
    }

    /**
     * Convert the pattern to individual links.
     *
     * @param linkWithPattern pattern we want to match.
     */
    private fun addLinksFromPattern(linkWithPattern: Link) {
        val pattern = linkWithPattern.pattern
        val m = pattern?.matcher(text!!) ?: return

        while (m.find()) {
            links.add(Link(linkWithPattern).setText(text!!.subSequence(m.start(), m.end()).toString()))

            // if we are only looking for the first occurrence of this pattern,
            // then quit now and don't look any further
            if (findOnlyFirstMatch) {
                break
            }
        }
    }

    /**
     * Manages the start and end points of the linked text.
     */
    private class Range(var start: Int, var end: Int)

    companion object {

        private const val TYPE_TEXT = 1
        private const val TYPE_TEXT_VIEW = 2

        fun from(context: Context, text: String): LinkBuilder {
            return LinkBuilder(TYPE_TEXT)
                    .setContext(context)
                    .setText(text)
        }

        fun on(tv: TextView): LinkBuilder {
            return LinkBuilder(TYPE_TEXT_VIEW)
                    .setContext(tv.context)
                    .setTextView(tv)
        }
    }
}
