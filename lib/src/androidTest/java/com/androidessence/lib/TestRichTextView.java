package com.androidessence.lib;

import android.graphics.Color;
import android.graphics.Typeface;
import android.test.AndroidTestCase;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;

import java.util.EnumSet;

/**
 * Test cases against the various public methods of the RichTextView class.
 *
 * Created by adammcneilly on 4/3/16.
 */
public class TestRichTextView extends AndroidTestCase {
    /**
     * The RichTextView that test cases will act upon.
     */
    private RichTextView mRichTextView;

    /**
     * The initial text for the RichTextView.
     */
    private static final String INITIAL_TEXT = "Hello, world!";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create new RichTextView to test with.
        mRichTextView = new RichTextView(mContext);
        mRichTextView.setText(INITIAL_TEXT);
    }

    /**
     * Adds a bold span to the RichTextView and verifies it was successfully added.
     */
    public void testAddBoldSpan() {
        // Format first word
        mRichTextView.formatSpan(0, 5, RichTextView.FormatType.BOLD);

        // Get spans
        Object[] spans = mRichTextView.getSpans();

        // Assert we only have one
        assertEquals(1, spans.length);

        // Get first one
        // Assert it is a StyleSpan
        // Assert it is bold.
        Object firstSpan = spans[0];
        assertTrue(firstSpan instanceof StyleSpan);
        assertTrue(((StyleSpan)firstSpan).getStyle() == Typeface.BOLD);
    }

    /**
     * Adds an italic span to the RichTextView and verifies it was successfully added.
     */
    public void testAddItalicSpan() {
        // Format first word
        mRichTextView.formatSpan(0, 5, RichTextView.FormatType.ITALIC);

        // Get spans
        Object[] spans = mRichTextView.getSpans();

        // Assert we only have one
        assertEquals(1, spans.length);

        // Get first one
        // Assert it is a StyleSpan
        // Assert it is italic.
        Object firstSpan = spans[0];
        assertTrue(firstSpan instanceof StyleSpan);
        assertTrue(((StyleSpan)firstSpan).getStyle() == Typeface.ITALIC);
    }

    /**
     * Adds an underline span to the RichTextView and verifies it was successfully added.
     */
    public void testAddUnderlineSpan() {
        // Format first word
        mRichTextView.formatSpan(0, 5, RichTextView.FormatType.UNDERLINE);

        // Get spans
        Object[] spans = mRichTextView.getSpans();

        // Assert we only have one
        assertEquals(1, spans.length);

        // Get first one
        // Assert it is italic.
        Object firstSpan = spans[0];
        assertTrue(firstSpan instanceof UnderlineSpan);
    }

    /**
     * Adds a strikethrough span to the RichTextView and verifies it was successfully added.
     */
    public void testAddStrikethroughSpan() {
        // Format first word
        mRichTextView.formatSpan(0, 5, RichTextView.FormatType.STRIKETHROUGH);

        // Get spans
        Object[] spans = mRichTextView.getSpans();

        // Assert we only have one
        assertEquals(1, spans.length);

        // Get first one
        // Assert it is strikethrough.
        Object firstSpan = spans[0];
        assertTrue(firstSpan instanceof StrikethroughSpan);
    }

    /**
     * Adds a superscript span to the RichTextView and verifies it was successfully added.
     */
    public void testAddSuperscriptSpan() {
        // Format first word
        mRichTextView.formatSpan(0, 5, RichTextView.FormatType.SUPERSCRIPT);

        // Get spans
        Object[] spans = mRichTextView.getSpans();

        // Assert we only have one
        assertEquals(1, spans.length);

        // Get first one
        // Assert it is superscript.
        Object firstSpan = spans[0];
        assertTrue(firstSpan instanceof SuperscriptSpan);
    }

    /**
     * Adds a subscript span to the RichTextView and verifies it was successfully added.
     */
    public void testAddSubscriptSpan() {
        // Format first word
        mRichTextView.formatSpan(0, 5, RichTextView.FormatType.SUBSCRIPT);

        // Get spans
        Object[] spans = mRichTextView.getSpans();

        // Assert we only have one
        assertEquals(1, spans.length);

        // Get first one
        // Assert it is subscript.
        Object firstSpan = spans[0];
        assertTrue(firstSpan instanceof SubscriptSpan);
    }

    /**
     * Adds multiple fomratting spans to the RichTextView and verifies they were successfully added.
     */
    public void testAddMultipleSpan() {
        // Format first word bold and italic
        mRichTextView.formatSpan(0, 5, EnumSet.of(RichTextView.FormatType.BOLD, RichTextView.FormatType.ITALIC));

        // Get spans
        Object[] spans = mRichTextView.getSpans();

        // Assert we only have two.
        assertEquals(2, spans.length);

        // Get first one
        // Assert it is a StyleSpan
        // Assert it is bold
        Object firstSpan = spans[0];
        assertTrue(firstSpan instanceof StyleSpan);
        assertTrue(((StyleSpan)firstSpan).getStyle() == Typeface.BOLD);

        // Get second one
        // Assert it is a StyleSpan
        // Assert it is italic
        Object secondSpan = spans[1];
        assertTrue(secondSpan instanceof StyleSpan);
        assertTrue(((StyleSpan)secondSpan).getStyle() == Typeface.ITALIC);
    }

    /**
     * Adds a foreground color span to the RichTextView and verifies it was successfully added.
     */
    public void testAddForegroundColorSpan() {
        // Format first word
        mRichTextView.colorSpan(0, 5, RichTextView.ColorFormatType.FOREGROUND, Color.BLUE);

        // Get spans
        Object[] spans = mRichTextView.getSpans();

        // Assert we only have one
        assertEquals(1, spans.length);

        // Get first one
        // Assert it is color.
        // Assert it is blue.
        Object firstSpan = spans[0];
        assertTrue(firstSpan instanceof ForegroundColorSpan);
        assertTrue(((ForegroundColorSpan)firstSpan).getForegroundColor() == Color.BLUE);
    }

    /**
     * Adds a highlight color span to the RichTextView and verifies it was successfully added.
     */
    public void testAddHighlightColorSpan() {
        // Format first word
        mRichTextView.colorSpan(0, 5, RichTextView.ColorFormatType.HIGHLIGHT, Color.BLUE);

        // Get spans
        Object[] spans = mRichTextView.getSpans();

        // Assert we only have one
        assertEquals(1, spans.length);

        // Get first one
        // Assert it is color.
        // Assert it is blue.
        Object firstSpan = spans[0];
        assertTrue(firstSpan instanceof BackgroundColorSpan);
        assertTrue(((BackgroundColorSpan)firstSpan).getBackgroundColor() == Color.BLUE);
    }

    /**
     * Adds a span to the RichTextView and verifies the getSpanCount method.
     */
    public void testGetSpanCount() {
        mRichTextView.formatSpan(0, 5, RichTextView.FormatType.BOLD);

        // Assert span count is 1
        assertEquals(1, mRichTextView.getSpanCount());
    }

    /**
     * Adds a span and removes it and verifies the span count.
     */
    public void testClearSpans() {
        mRichTextView.formatSpan(0, 5, RichTextView.FormatType.BOLD);

        // Remove and assert span count is 0
        mRichTextView.clearSpans();
        assertEquals(0, mRichTextView.getSpanCount());
    }

    /**
     * Tries adding an invalid format type (null) and verifying an exception is thrown.
     */
    public void testInvalidColorFormatType() {
        try {
            mRichTextView.colorSpan(0, 5, null, Color.BLUE);
            fail();
        } catch(IllegalArgumentException iae) {
            //Do nothing, we passed!
        }
    }
}
