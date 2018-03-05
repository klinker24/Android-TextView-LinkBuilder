package com.klinker.android.link_builder

import android.content.Context
import android.text.method.MovementMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView

class LinkConsumableTextView : TextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * Overriding the methods below is required if you want to use ListView's OnItemClickListener.
     *
     * These methods will force the list view to only consume the touch event if the TouchableSpan
     * has been clicked. Otherwise, it will not consume the touch event and it will defer to your
     * ListView.OnItemClickListener method.
     */

    override fun hasFocusable() = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        val movementMethod = movementMethod

        if (movementMethod is TouchableMovementMethod) {
            val span = movementMethod.pressedSpan

            if (span != null) {
                return true
            }
        }

        return false
    }
}
