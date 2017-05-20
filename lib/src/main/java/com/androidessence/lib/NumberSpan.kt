package com.androidessence.lib

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.Spanned
import android.text.style.LeadingMarginSpan

/*
 * Copyright (C) 2015-2016 Emanuel Moecklin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * Paragraph numbering.
 *
 * Android seems to add the leading margin for an empty paragraph to the previous paragraph
 * (]0, 4][4, 4] --> the leading margin of the second span is added to the ]0, 4] paragraph regardless of the Spanned.flags)
 * --> therefore we ignore the leading margin for the last, empty paragraph unless it's the only one
 *
 * Created by Raghunandan on 30-07-2016.
 */
@SuppressLint("ParcelCreator")
class NumberSpan(private val mNr: Int, private val mGapWidth: Int, private val mIgnoreSpan: Boolean, textSize: Float) : LeadingMarginSpan {

    private var mTextSize = 20f
    private var mWidth: Float = 0.toFloat()

    init {
        mTextSize = textSize
    }

    override fun getLeadingMargin(first: Boolean): Int {
        return mGapWidth//mIgnoreSpan ? 0 : Math.max(Math.round(mWidth + 2), mGapWidth);
    }

    override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int, top: Int, baseline: Int, bottom: Int,
                                   text: CharSequence, start: Int, end: Int, first: Boolean, l: Layout) {

        val spanned = text as Spanned

        if (!mIgnoreSpan && spanned.getSpanStart(this) == start) {
            // set paint
            val oldStyle = p.style
            val oldTextSize = p.textSize
            p.style = Paint.Style.FILL
            // mTextSize = baseline - top;
            p.textSize = mTextSize
            mWidth = p.measureText(mNr.toString() + ".")

            // draw the number
            c.drawText(mNr.toString() + ".", x.toFloat(), baseline.toFloat(), p)

            // restore paint
            p.style = oldStyle
            p.textSize = oldTextSize
        }
    }
}
