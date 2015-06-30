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

import java.util.regex.Pattern;

public class Link {

    private static final int DEFAULT_COLOR = Color.parseColor("#33B5E5");
    private static final float DEFAULT_ALPHA = .20f;

    private String text;
    private Pattern pattern;
    private int textColor = DEFAULT_COLOR;
    private float highlightAlpha = DEFAULT_ALPHA;
    private boolean underlined = true;

    private OnClickListener clickListener;
    private OnLongClickListener longClickListener;

    /**
     * Copy Constructor.
     * @param link what you want to base the new link off of.
     */
    public Link(Link link) {
        this.text = link.getText();
        this.pattern = link.getPattern();
        this.clickListener = link.getClickListener();
        this.longClickListener = link.getLongClickListener();
        this.textColor = link.getTextColor();
        this.highlightAlpha = link.getHighlightAlpha();
        this.underlined = link.isUnderlined();
    }

    /**
     * Construct a new Link rule to match the text.
     * @param text Text you want to highlight.
     */
    public Link(String text) {
        this.text = text;
        this.pattern = null;
    }

    /**
     * Construct a new Link rule to match the pattern.
     * @param pattern pattern of the different texts you want to highlight.
     */
    public Link(Pattern pattern) {
        this.pattern = pattern;
        this.text = null;
    }

    /**
     * Specify the text you want to match.
     * @param text to match.
     * @return the current link object.
     */
    public Link setText(String text) {
        this.text = text;
        this.pattern = null;
        return this;
    }

    /**
     * Specify the pattern you want to match.
     * @param pattern to match.
     * @return the current link object.
     */
    public Link setPattern(Pattern pattern) {
        this.pattern = pattern;
        this.text = null;
        return this;
    }

    /**
     * Specify what happens with a short click.
     * @param clickListener action for the short click.
     * @return the current link object.
     */
    public Link setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    /**
     * Specify what happens with a long click.
     * @param longClickListener action for the long click.
     * @return the current link object.
     */
    public Link setOnLongClickListener(OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
        return this;
    }

    /**
     * Specify the text color for the linked text.
     * @param color as an integer (not resource).
     * @return the current link object.
     */
    public Link setTextColor(int color) {
        this.textColor = color;
        return this;
    }

    /**
     * Specify whether you want it underlined or not.
     * @param underlined
     * @return the current link object.
     */
    public Link setUnderlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    /**
     * Specify the alpha of the links background when the user clicks it.
     * @param alpha
     * @return the current link object.
     */
    public Link setHighlightAlpha(float alpha) {
        this.highlightAlpha = alpha;
        return this;
    }

    public String getText() {
        return text;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getTextColor() {
        return textColor;
    }

    public float getHighlightAlpha() {
        return highlightAlpha;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public OnClickListener getClickListener() {
        return clickListener;
    }

    public OnLongClickListener getLongClickListener() {
        return longClickListener;
    }

    /**
     * Interface to manage the single clicks.
     */
    public interface OnClickListener {
        void onClick(String clickedText);
    }

    /**
     * Interface to manage the long clicks.
     */
    public interface OnLongClickListener {
        void onLongClick(String clickedText);
    }
}
