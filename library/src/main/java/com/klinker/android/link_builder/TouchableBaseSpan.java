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

import android.os.Handler;
import android.text.style.ClickableSpan;
import android.view.View;

public abstract class TouchableBaseSpan extends ClickableSpan {

    public boolean touched = false;

    /**
     * This TouchableSpan has been clicked.
     * @param widget TextView containing the touchable span
     */
    @Override
    public void onClick(View widget) {
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TouchableMovementMethod.touched = false;
            }
        }, 500);
    }

    public boolean isTouched() {
        return touched;
    }

    /**
     * Specifiy whether or not the link is currently touched
     * @param touched
     */
    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}
