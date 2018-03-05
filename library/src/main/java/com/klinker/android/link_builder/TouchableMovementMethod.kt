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

import android.os.Handler
import android.text.Layout
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.widget.TextView

class TouchableMovementMethod : LinkMovementMethod() {

    var pressedSpan: TouchableBaseSpan? = null
        private set

    /**
     * Manages the touches to find the link that was clicked and highlight it
     * @param textView view the user clicked
     * @param spannable spannable string inside the clicked view
     * @param event motion event that occurred
     * @return
     */
    override fun onTouchEvent(textView: TextView, spannable: Spannable, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            pressedSpan = getPressedSpan(textView, spannable, event)

            if (pressedSpan != null) {
                pressedSpan!!.isTouched = true
                touched = true

                Handler().postDelayed({
                    if (touched && pressedSpan != null) {
                        if (textView.isHapticFeedbackEnabled)
                            textView.isHapticFeedbackEnabled = true
                        textView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)

                        pressedSpan!!.onLongClick(textView)
                        pressedSpan!!.isTouched = false
                        pressedSpan = null

                        Selection.removeSelection(spannable)
                    }
                }, 500)

                Selection.setSelection(spannable, spannable.getSpanStart(pressedSpan),
                        spannable.getSpanEnd(pressedSpan))
            }
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            val touchedSpan = getPressedSpan(textView, spannable, event)

            if (pressedSpan != null && pressedSpan != touchedSpan) {
                pressedSpan!!.isTouched = false
                pressedSpan = null
                touched = false

                Selection.removeSelection(spannable)
            }
        } else if (event.action == MotionEvent.ACTION_UP) {
            if (pressedSpan != null) {
                pressedSpan!!.onClick(textView)
                pressedSpan!!.isTouched = false
                pressedSpan = null

                Selection.removeSelection(spannable)
            }
        } else {
            if (pressedSpan != null) {
                pressedSpan!!.isTouched = false
                touched = false

                super.onTouchEvent(textView, spannable, event)
            }

            pressedSpan = null

            Selection.removeSelection(spannable)
        }

        return true
    }

    /**
     * Find the span that was clicked
     * @param widget view the user clicked
     * @param spannable spannable string inside the clicked view
     * @param event motion event that occurred
     * @return the touchable span that was pressed
     */
    private fun getPressedSpan(widget: TextView, spannable: Spannable, event: MotionEvent): TouchableBaseSpan? {

        var x = event.x.toInt()
        var y = event.y.toInt()

        x -= widget.totalPaddingLeft
        y -= widget.totalPaddingTop

        x += widget.scrollX
        y += widget.scrollY

        val layout = widget.layout
        val line = layout.getLineForVertical(y)
        val off = layout.getOffsetForHorizontal(line, x.toFloat())
        val end = layout.getLineEnd(line)

        // offset seems like it can be one off in some cases
        // Could be what was causing issue 7 in the first place:
        // https://github.com/klinker24/Android-TextView-LinkBuilder/issues/7
        if (off != end && off != end - 1) {
            val link = spannable.getSpans(off, off, TouchableBaseSpan::class.java)

            if (link.isNotEmpty())
                return link[0]
        }

        return null
    }

    companion object {

        private var sInstance: TouchableMovementMethod? = null
        var touched = false

        /**
         * Don't need to create a new instance for every text view. We can re-use them
         * @return Instance of the movement method
         */
        val instance: MovementMethod
            get() {
                if (sInstance == null)
                    sInstance = TouchableMovementMethod()

                return sInstance!!
            }
    }
}