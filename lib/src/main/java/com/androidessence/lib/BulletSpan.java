package com.androidessence.lib;

/**
 * Created by Raghunandan on 28-11-2016.
 */

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;


@SuppressLint("ParcelCreator")
public class BulletSpan implements LeadingMarginSpan {


    private final int mGapWidth;
    private final boolean mIgnoreSpan;


    public BulletSpan(int gapWidth, boolean ignoreSpan) {
        mGapWidth = gapWidth;
        mIgnoreSpan = ignoreSpan;

    }

    @Override
    public int getLeadingMargin(boolean first) {
        return mGapWidth;//mIgnoreSpan ? 0 : Math.max(Math.round(mWidth + 2), mGapWidth);
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom,
                                  CharSequence text, int start, int end, boolean first, Layout l) {
        Spanned spanned = (Spanned) text;
        if (!mIgnoreSpan && spanned.getSpanStart(this) == start) {
            // set paint
            p.setStyle(Paint.Style.FILL);

            c.drawCircle(x + dir * 10, (top + bottom) / 2.0f,
                    10, p);



        }
    }
}
