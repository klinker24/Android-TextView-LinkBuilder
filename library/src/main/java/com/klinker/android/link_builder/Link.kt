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

import android.graphics.Color
import android.graphics.Typeface

import java.util.regex.Pattern

@Suppress("MemberVisibilityCanBePrivate")
class Link {

    @JvmField var text: String? = null
    @JvmField var pattern: Pattern? = null
    @JvmField var prependedText: String? = null
    @JvmField var appendedText: String? = null
    @JvmField var textColor = 0
    @JvmField var textColorOfHighlightedLink = 0
    @JvmField var highlightAlpha = DEFAULT_ALPHA
    @JvmField var underlined = true
    @JvmField var bold = false
    @JvmField var typeface: Typeface? = null
    @JvmField var clickListener: OnClickListener? = null
    @JvmField var longClickListener: OnLongClickListener? = null

    /**
     * Copy Constructor.
     * @param link what you want to base the new link off of.
     */
    constructor(link: Link) {
        this.text = link.text
        this.prependedText = link.prependedText
        this.appendedText = link.appendedText
        this.pattern = link.pattern
        this.clickListener = link.clickListener
        this.longClickListener = link.longClickListener
        this.textColor = link.textColor
        this.textColorOfHighlightedLink = link.textColorOfHighlightedLink
        this.highlightAlpha = link.highlightAlpha
        this.underlined = link.underlined
        this.bold = link.bold
        this.typeface = link.typeface
    }

    /**
     * Construct a new Link rule to match the text.
     * @param text Text you want to highlight.
     */
    constructor(text: String) {
        this.text = text
        this.pattern = null
    }

    /**
     * Construct a new Link rule to match the pattern.
     * @param pattern pattern of the different texts you want to highlight.
     */
    constructor(pattern: Pattern) {
        this.pattern = pattern
        this.text = null
    }

    /**
     * Specify the text you want to match.
     * @param text to match.
     * @return the current link object.
     */
    fun setText(text: String): Link {
        this.text = text
        this.pattern = null
        return this
    }

    /**
     * Specify the pattern you want to match.
     * @param pattern to match.
     * @return the current link object.
     */
    fun setPattern(pattern: Pattern): Link {
        this.pattern = pattern
        this.text = null
        return this
    }

    /**
     * This text will be added *before* any matches.
     * @param text to place before the link's text.
     * @return the current link object.
     */
    fun setPrependedText(text: String): Link {
        this.prependedText = text
        return this
    }

    /**
     * This text will be added *after* any matches.
     * @param text to place after the link's text.
     * @return the current link object.
     */
    fun setAppendedText(text: String): Link {
        this.appendedText = text
        return this
    }

    /**
     * Specify what happens with a short click.
     * @param clickListener action for the short click.
     * @return the current link object.
     */
    fun setOnClickListener(clickListener: OnClickListener): Link {
        this.clickListener = clickListener
        return this
    }

    fun setOnClickListener(listener: (String) -> Unit): Link {
        this.clickListener = object : OnClickListener {
            override fun onClick(clickedText: String) {
                listener(clickedText)
            }
        }

        return this
    }

    /**
     * Specify what happens with a long click.
     * @param longClickListener action for the long click.
     * @return the current link object.
     */
    fun setOnLongClickListener(longClickListener: OnLongClickListener): Link {
        this.longClickListener = longClickListener
        return this
    }

    fun setOnLongClickListener(listener: (String) -> Unit): Link {
        this.longClickListener = object : OnLongClickListener {
            override fun onLongClick(clickedText: String) {
                listener(clickedText)
            }
        }

        return this
    }

    /**
     * Specify the text color for the linked text.
     * @param color as an integer (not resource).
     * @return the current link object.
     */
    fun setTextColor(color: Int): Link {
        this.textColor = color
        return this
    }

    /**
     * Specify the text color for the linked text when the link is pressed.
     * @param colorOfHighlightedLink as an integer (not resource).
     * @return the current link object.
     */
    fun setTextColorOfHighlightedLink(colorOfHighlightedLink: Int): Link {
        this.textColorOfHighlightedLink = colorOfHighlightedLink
        return this
    }

    /**
     * Specify whether you want it underlined or not.
     * @param underlined
     * @return the current link object.
     */
    fun setUnderlined(underlined: Boolean): Link {
        this.underlined = underlined
        return this
    }

    /**
     * Specify whether you want it bold or not.
     * @param bold
     * @return the current link object.
     */
    fun setBold(bold: Boolean): Link {
        this.bold = bold
        return this
    }

    /**
     * Specify the alpha of the links background when the user clicks it.
     * @param alpha
     * @return the current link object.
     */
    fun setHighlightAlpha(alpha: Float): Link {
        this.highlightAlpha = alpha
        return this
    }

    /**
     * Specify the typeface for the link.
     * @param typeface
     * @return the current link object
     */
    fun setTypeface(typeface: Typeface): Link {
        this.typeface = typeface
        return this
    }

    /**
     * Interface to manage the single clicks.
     */
    @FunctionalInterface
    interface OnClickListener {
        fun onClick(clickedText: String)
    }

    /**
     * Interface to manage the long clicks.
     */
    @FunctionalInterface
    interface OnLongClickListener {
        fun onLongClick(clickedText: String)
    }

    companion object {
        val DEFAULT_COLOR = Color.parseColor("#33B5E5")
        private const val DEFAULT_ALPHA = .20f
    }
}
