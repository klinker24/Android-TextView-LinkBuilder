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

import android.graphics.Color;
import android.os.Handler;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class TouchableSpan extends ClickableSpan {

    private final Link link;
    public boolean touched = false;

    /**
     * Construct new TouchableSpan using the link
     * @param link
     */
    public TouchableSpan(Link link) {
        this.link = link;
    }

    /**
     * This TouchableSpan has been clicked.
     * @param widget TextView containing the touchable span
     */
    public void onClick(View widget) {

        // handle the click
        if (link.getClickListener() != null) {
            link.getClickListener().onClick(link.getText());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TouchableMovementMethod.touched = false;
            }
        }, 500);
    }

    /**
     * This TouchableSpan has been long clicked.
     * @param widget TextView containing the touchable span
     */
    public void onLongClick(View widget) {

        // handle the long click
        if (link.getLongClickListener() != null) {
            link.getLongClickListener().onLongClick(link.getText());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TouchableMovementMethod.touched = false;
            }
        }, 500);
    }

    /**
     * Set the alpha for the color based on the alpha factor
     * @param color original color
     * @param factor how much we want to scale the alpha to
     * @return new color with scaled alpha
     */
    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * Draw the links background and set whether or not we want it to be underlined
     * @param ds the link
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setUnderlineText(link.isUnderlined());
        ds.setColor(link.getTextColor());
        ds.bgColor = touched ? adjustAlpha(link.getTextColor(), link.getHighlightAlpha()) : Color.TRANSPARENT;
    }

    /**
     * Specifiy whether or not the link is currently touched
     * @param touched
     */
    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}