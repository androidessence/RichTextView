package com.androidessence.lib

import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

/**
 * Span that allows text to fade in.
 *
 * Created by Raghunandan on 28-11-2016.
 */
class FadeInSpan : CharacterStyle(), UpdateAppearance {

    var alpha: Int = 0

    override fun updateDrawState(paint: TextPaint) {
        paint.alpha = alpha
    }
}