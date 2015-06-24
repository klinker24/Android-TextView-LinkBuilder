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

package com.klinker.android.link_builder;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

public class TouchableMovementMethod extends LinkMovementMethod {

    // TouchableSpan to handle the clicks
    private TouchableSpan mPressedSpan;

    /**
     * Manages the touches to find the link that was clicked and highlight it
     * @param textView view the user clicked
     * @param spannable spannable string inside the clicked view
     * @param event motion event that occurred
     * @return
     */
    @Override
    public boolean onTouchEvent(final TextView textView, final Spannable spannable, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPressedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null) {
                mPressedSpan.setTouched(true);
                touched = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (touched && mPressedSpan != null) {
                            try {
                                Vibrator v = (Vibrator) textView.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                                v.vibrate(25);
                            } catch (SecurityException e) {
                                e.printStackTrace();
                                // would vibrate here with the correct permissions.
                            }

                            mPressedSpan.onLongClick(textView);
                            mPressedSpan.setTouched(false);
                            mPressedSpan = null;
                            Selection.removeSelection(spannable);
                        }
                    }
                }, 500);
                Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan),
                        spannable.getSpanEnd(mPressedSpan));
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            TouchableSpan touchedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null && touchedSpan != mPressedSpan) {
                mPressedSpan.setTouched(false);
                touched = false;
                mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
            if (mPressedSpan != null) {
                mPressedSpan.onClick(textView);
                mPressedSpan.setTouched(false);
                mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
        } else {
            if (mPressedSpan != null) {
                mPressedSpan.setTouched(false);
                touched = false;
                super.onTouchEvent(textView, spannable, event);
            }
            mPressedSpan = null;
            Selection.removeSelection(spannable);
        }
        return true;
    }

    /**
     * Find the span that was clicked
     * @param widget view the user clicked
     * @param spannable spannable string inside the clicked view
     * @param event motion event that occurred
     * @return the touchable span that was pressed
     */
    private TouchableSpan getPressedSpan(TextView widget, Spannable spannable, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= widget.getTotalPaddingLeft();
        y -= widget.getTotalPaddingTop();

        x += widget.getScrollX();
        y += widget.getScrollY();

        Layout layout = widget.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        TouchableSpan[] link = spannable.getSpans(off, off, TouchableSpan.class);

        TouchableSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }

    private static TouchableMovementMethod sInstance;
    public static boolean touched = false;

    /**
     * Don't need to create a new instance for every text view. We can re-use them
     * @return Instance of the movement method
     */
    public static MovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new TouchableMovementMethod();

        return sInstance;
    }
}