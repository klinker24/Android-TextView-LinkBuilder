package com.klinker.android.link_builder;

import android.content.Context;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class LinkConsumableTextView extends TextView {

    public LinkConsumableTextView(Context context) {
        super(context);
    }

    public LinkConsumableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkConsumableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Overriding the methods below is required if you want to use ListView's OnItemClickListener.
     *
     * These methods will force the list view to only consume the touch event if the TouchableSpan
     * has been clicked. Otherwise, it will not consume the touch event and it will defer to your
     * ListView.OnItemClickListener method.
     */

    @Override
    public boolean hasFocusable() {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        MovementMethod movementMethod = getMovementMethod();

        if (movementMethod instanceof TouchableMovementMethod) {
            TouchableBaseSpan span = ((TouchableMovementMethod) movementMethod).getPressedSpan();

            if (span != null) {
                return true;
            }
        }

        return false;
    }
}
