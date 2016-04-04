package com.androidessence.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.EnumSet;

/**
 * Rich TextView Component that is an enhanced version of a TextView with certain styling.
 *
 * Created by adammcneilly on 4/1/16.
 */
public class RichTextView extends TextView{
    //-- Properties --//

    /**
     * The Context that this view is displayed in.
     */
    private Context mContext;

    /**
     * The spannable string to display in this TextView.
     */
    private SpannableString mSpannableString;

    /**
     * The number of spans applied to this SpannableString.
     */
    private int mSpanCount;

    //-- Constructors --//

    public RichTextView(Context context) {
        this(context, null);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);

        // Set variables.
        this.mContext = context;
        this.mSpanCount = 0;

        initStyle(attrs, defStyleAttr);
    }

    //-- Initializations --//

    private void initStyle(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RichTextView, defStyleAttr, 0);

        if(typedArray != null) {
            //TODO: Implement special attributes.

            typedArray.recycle();
        }
    }

    //-- Overridden methods --//

    @Override
    public void setText(CharSequence text, BufferType type) {
        // Set our spannable string.
        mSpannableString = new SpannableString(text);
        super.setText(mSpannableString, type);
    }


    //-- Format methods --//

    /**
     * Formats a Span of text in the text view. This method was added as a convenience method
     * so the user doesn't have to use EnumSet for one item.
     * @param start The index of the first character to span.
     * @param end The index of the last character to span.
     * @param formatType The format to apply to this span.
     */
    public void formatSpan(int start, int end, FormatType formatType) {
        // Call the other method as if this were an EnumSet.
        formatSpan(start, end, EnumSet.of(formatType));
    }

    /**
     * Formats a Span of text in the text view.
     * @param start The index of the first character to span.
     * @param end The index of the last character to span.
     * @param formatTypes The formats to apply to this span.
     */
    public void formatSpan(int start, int end, EnumSet<FormatType> formatTypes) {
        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if(start < 0 || start >= mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid start index.");
        } else if(end < start || end > mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid end index.");
        }
        // There is no need to consider a null FormatType. Since we have two method headers of (int, int, type)
        // The compiler will claim that `formatSpan(int, int, null);` call is ambiguous.

        mSpanCount += formatTypes.size();

        // Create span to be applied - Default to normal.
        for(FormatType type : formatTypes) {
            mSpannableString.setSpan(type.getSpan(), start, end, 0);
        }

        setText(mSpannableString);
    }

    /**
     * Colors a portion of the string.
     * @param start The index of the first character to color.
     * @param end The index of the last character to color..
     * @param formatType The type of format to apply to this span.
     * @param color The color to apply to this substring.
     */
    public void colorSpan(int start, int end, ColorFormatType formatType, int color) {
        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if(start < 0 || start >= mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid start index.");
        } else if(end < start || end > mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid end index.");
        } else if (formatType == null) {
            throw new IllegalArgumentException("Invalid FormatType.");
        }

        // Add span
        mSpanCount++;
        mSpannableString.setSpan(formatType.getSpan(color), start, end, 0);

        // Set text
        setText(mSpannableString);
    }

    /**
     * Clears all spans applied to our SpannableString.
     */
    public void clearSpans() {
        // Get spans
        Object[] spans = mSpannableString.getSpans(0, mSpanCount, Object.class);

        // Loop through and remove each one.
        for(Object span : spans) {
            mSpannableString.removeSpan(span);
            mSpanCount--;
        }

        // Set text again.
        setText(mSpannableString);
    }

    //-- Accessors --//

    /**
     * Retrieves the number of spans applied to this SpannableString.
     * @return The number of spans applied.
     */
    public int getSpanCount() {
        return mSpanCount;
    }

    /**
     * Retrieves all spans applied to the string.
     * @return An array of object representing the types of spans applied.
     */
    public Object[] getSpans() {
        return mSpannableString.getSpans(0, mSpanCount, Object.class);
    }

    //-- Inner classes --//

    /**
     * An enumeration of various ways to format the text.
     */
    public enum FormatType {
        NONE {
            @Override
            public Object getSpan() {
                return new StyleSpan(Typeface.NORMAL);
            }
        },

        BOLD {
            @Override
            public Object getSpan() {
                return new StyleSpan(Typeface.BOLD);
            }
        },

        ITALIC {
            @Override
            public Object getSpan() {
                return new StyleSpan(Typeface.ITALIC);
            }
        },

        UNDERLINE {
            @Override
            public Object getSpan() {
                return new UnderlineSpan();
            }
        },

        STRIKETHROUGH {
            @Override
            public Object getSpan() {
                return new StrikethroughSpan();
            }
        },

        SUPERSCRIPT {
            @Override
            public Object getSpan() {
                return new SuperscriptSpan();
            }
        },

        SUBSCRIPT {
            @Override
            public Object getSpan() {
                return new SubscriptSpan();
            }
        };

        /**
         * @return The type of Span to apply for this FormatType.
         */
        public abstract Object getSpan();
    }

    /**
     * An enumeration of various ways to color the text.
     */
    public enum ColorFormatType {
        FOREGROUND {
            @Override
            public Object getSpan(int color) {
                return new ForegroundColorSpan(color);
            }
        },

        HIGHLIGHT {
            @Override
            public Object getSpan(int color) {
                return new BackgroundColorSpan(color);
            }
        };

        /**
         * @param color The color to apply to this span.
         * @return The type of Span to apply for this ColorformatType.
         */
        public abstract Object getSpan(int color);
    }
}
