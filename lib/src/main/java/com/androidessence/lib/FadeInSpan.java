package com.androidessence.lib;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * Created by Raghunandan on 28-11-2016.
 */

public class FadeInSpan extends CharacterStyle implements UpdateAppearance {

    private int alpha;

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public FadeInSpan() {

    }

    @Override
    public void updateDrawState(TextPaint paint) {
        paint.setAlpha(alpha);

    }
}