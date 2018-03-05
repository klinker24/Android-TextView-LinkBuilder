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
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Handler
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.View

class TouchableSpan(context: Context, private val link: Link) : TouchableBaseSpan() {

    private var textColor: Int = 0
    private var textColorOfHighlightedLink: Int = 0

    init {
        if (link.textColor == 0) {
            this.textColor = getDefaultColor(context, R.styleable.LinkBuilder_defaultLinkColor)
        } else {
            this.textColor = link.textColor
        }

        if (link.textColorOfHighlightedLink == 0) {
            this.textColorOfHighlightedLink = getDefaultColor(context, R.styleable.LinkBuilder_defaultTextColorOfHighlightedLink)

            // don't use the default of light blue for this color
            if (this.textColorOfHighlightedLink == Link.DEFAULT_COLOR) {
                this.textColorOfHighlightedLink = textColor
            }
        } else {
            this.textColorOfHighlightedLink = link.textColorOfHighlightedLink
        }
    }

    /**
     * Finds the default color for links based on the current theme.
     * @param context activity
     * @param index index of attribute to retrieve based on current theme
     * @return color as an integer
     */
    private fun getDefaultColor(context: Context, index: Int): Int {
        val array = obtainStyledAttrsFromThemeAttr(context, R.attr.linkBuilderStyle, R.styleable.LinkBuilder)
        val color = array.getColor(index, Link.DEFAULT_COLOR)
        array.recycle()

        return color
    }

    /**
     * This TouchableSpan has been clicked.
     * @param widget TextView containing the touchable span
     */
    override fun onClick(widget: View) {
        if (link.text != null) {
            link.clickListener?.onClick(link.text!!)
        }

        super.onClick(widget)
    }

    /**
     * This TouchableSpan has been long clicked.
     * @param widget TextView containing the touchable span
     */
    override fun onLongClick(widget: View) {
        if (link.text != null) {
            link.longClickListener?.onLongClick(link.text!!)
        }

        super.onLongClick(widget)
    }

    /**
     * Set the alpha for the color based on the alpha factor
     * @param color original color
     * @param factor how much we want to scale the alpha to
     * @return new color with scaled alpha
     */
    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        return Color.argb(alpha, red, green, blue)
    }

    /**
     * Draw the links background and set whether or not we want it to be underlined or bold
     * @param ds the link
     */
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)

        ds.isUnderlineText = link.underlined
        ds.isFakeBoldText = link.bold
        ds.color = if (isTouched) textColorOfHighlightedLink else textColor
        ds.bgColor = if (isTouched) adjustAlpha(textColor, link.highlightAlpha) else Color.TRANSPARENT

        if (link.typeface != null) {
            ds.typeface = link.typeface
        }
    }

    companion object {

        private fun obtainStyledAttrsFromThemeAttr(context: Context, themeAttr: Int, styleAttrs: IntArray): TypedArray {
            // Need to get resource id of style pointed to from the theme attr
            val outValue = TypedValue()
            context.theme.resolveAttribute(themeAttr, outValue, true)
            val styleResId = outValue.resourceId

            // Now return the values (from styleAttrs) from the style
            return context.obtainStyledAttributes(styleResId, styleAttrs)
        }
    }
}