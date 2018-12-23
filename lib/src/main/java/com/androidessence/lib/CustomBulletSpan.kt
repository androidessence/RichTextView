package com.androidessence.lib

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.Spanned
import android.text.style.LeadingMarginSpan

/**
 * Allows a number of rows in a TextView to have bullets in front of them.
 *
 * Created by Raghunandan on 28-11-2016.
 */
class CustomBulletSpan(private val mGapWidth: Int, private val mIgnoreSpan: Boolean, private val bulletColor: Int, private val bulletRadius: Int) : LeadingMarginSpan {

    override fun getLeadingMargin(first: Boolean): Int {
        return mGapWidth//mIgnoreSpan ? 0 : Math.max(Math.round(mWidth + 2), mGapWidth);
    }

    override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int, top: Int, baseline: Int, bottom: Int,
                                   text: CharSequence, start: Int, end: Int, first: Boolean, l: Layout) {

        val spanned = text as Spanned

        if (!mIgnoreSpan && spanned.getSpanStart(this) == start) {
            // set paint
            val oldStyle = p.style
            val oldcolor = p.color
            p.style = Paint.Style.FILL
            p.color = bulletColor

            c.drawCircle((x + dir * 10).toFloat(), (top + bottom) / 2.0f,
                    bulletRadius.toFloat(), p)

            // this is required if not the color and style is carried to other spans.
            p.color = oldcolor
            p.style = oldStyle
        }
    }
}