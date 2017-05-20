package com.androidessence.lib;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Property;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.EnumSet;


/**
 * Rich TextView Component that is an enhanced version of a TextView with certain styling.
 * <p>
 * Created by adammcneilly on 4/1/16.
 */
public class RichTextView extends AppCompatTextView {
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

        //TODO: Implement special attributes.
        if (typedArray != null) typedArray.recycle();
    }

    //-- Overridden methods --//

    @Override
    public void setText(CharSequence text, BufferType type) {
        // Set our spannable string.
        mSpannableString = new SpannableString(text);
        super.setText(mSpannableString, type);
    }

    /**
     * Add a bullet at the start fo each line. You can specify start and endline
     *
     * @param start      The index at which the text starts to fade.
     * @param end        The index at which the text fade ends.
     * @param duration   The duration for the text fade.
     *
     */
    public void formatFadeInSpan(int start, int end,int duration)
    {

        mSpanCount++;
        final FadeInSpan span = new FadeInSpan();

        mSpannableString.setSpan(span, start, end, 0);

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(
                span, FADE_INT_PROPERTY, 0, 255);
        objectAnimator.setEvaluator(new IntEvaluator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setText(mSpannableString);
            }
        });
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    /**
     * Add a bullet at the start fo each line. You can specify start and endline
     *
     * @param startline      The start line at which bullet is shown.
     * @param endline        The end line at which bullet is shown.
     */
    public void formatBulletSpan(int startline,int endline) {

        mSpanCount++;
        String[] splitter = mSpannableString.toString().split("\n");

        int start = 0;

        for (int i = 0; i < splitter.length; i++) {
            //Log.d("Index : " + i, splitter[i]);
            if (!splitter[i].equals("") && !splitter[i].equals("\n")) {

                if(i>=(startline-1) && i<endline) {

                    mSpannableString.setSpan(new com.androidessence.lib.BulletSpan(100,false),start,(start+1),0);
                    start = start + splitter[i].length() + 1;
                }else
                {
                    start = start + splitter[i].length() + 1;
                }

            }
        }

        setText(mSpannableString);

    }

    /**
     * Add a bullet at the start fo each line. You can specify start and endline
     *
     * @param startline      The start line at which number is shown.
     * @param endline        The end line at which number is shown.
     */
    public void formatNumberSpan(int startline,int endline) {

        mSpanCount++;
        String[] splitter = mSpannableString.toString().split("\n");

        int start = 0;
        int index = 1;

        for (int i = 0; i < splitter.length; i++) {
            //Log.d("Index : " + i, splitter[i]);
            if (!splitter[i].equals("") && !splitter[i].equals("\n")) {

                if(i>=(startline-1) && i<endline) {
                    mSpannableString.setSpan(new NumberSpan(index++, 100, false, getTextSize()), start, (start + 1), 0);
                    start = start + splitter[i].length() + 1;
                }else
                {
                    start = start + splitter[i].length() + 1;
                }

            }
        }

        setText(mSpannableString);

    }

    /**
     * Add a Image at the specified index
     *
     * @param start    The start index where image is shown.
     * @param end      The end index where image is shown.
     */
    public void formatImageSpan(int start, int end, Bitmap drawable) {
        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if (start < 0 || start >= mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid start index.");
        } else if (end < start || end > mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid end index.");
        }

        // Add span
        mSpanCount++;
        mSpannableString.setSpan(new ImageSpan(getContext(), drawable), start, end, 0);

        // Set text
        setText(mSpannableString);
    }

    //-- Format methods --//

    /**
     * Formats a Span of text in the text view. This method was added as a convenience method
     * so the user doesn't have to use EnumSet for one item.
     *
     * @param start      The index of the first character to span.
     * @param end        The index of the last character to span.
     * @param formatType The format to apply to this span.
     */
    public void formatSpan(int start, int end, FormatType formatType) {
        // Call the other method as if this were an EnumSet.
        formatSpan(start, end, EnumSet.of(formatType));
    }

    /**
     * Formats a Span of text in the text view.
     *
     * @param start       The index of the first character to span.
     * @param end         The index of the last character to span.
     * @param formatTypes The formats to apply to this span.
     */
    public void formatSpan(int start, int end, EnumSet<FormatType> formatTypes) {


        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if (start < 0 || start >= mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid start index.");
        } else if (end < start || end > mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid end index.");
        }
        // There is no need to consider a null FormatType. Since we have two method headers of (int, int, type)
        // The compiler will claim that `formatSpan(int, int, null);` call is ambiguous.

        mSpanCount += formatTypes.size();

        // Create span to be applied - Default to normal.
        for (FormatType type : formatTypes) {

            mSpannableString.setSpan(type.getSpan(), start, end, 0);

        }


        setText(mSpannableString);
    }

    /**
     * Colors a portion of the string.
     *
     * @param start      The index of the first character to color.
     * @param end        The index of the last character to color..
     * @param formatType The type of format to apply to this span.
     * @param color      The color to apply to this substring.
     */
    public void colorSpan(int start, int end, ColorFormatType formatType, int color) {
        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if (start < 0 || start >= mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid start index.");
        } else if (end < start || end > mSpannableString.length()) {
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
     * Adds a hyperlink to a portion of the string.
     *
     * @param start      The index of the first character of the link.
     * @param end        The index of the last character  of the link.
     * @param url        The URL that the hyperlink points to.
     */
    public void addHyperlinkToSpan(int start, int end, final String url) {
        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if (start < 0 || start >= mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid start index.");
        } else if (end < start || end > mSpannableString.length()) {
            throw new IllegalArgumentException("Invalid end index.");
        }

        // Add span
        mSpanCount++;
        this.setMovementMethod(LinkMovementMethod.getInstance());

        URLSpan urlSpan = new URLSpan(url);
        mSpannableString.setSpan(urlSpan, start, end, 0);

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
        for (Object span : spans) {
            mSpannableString.removeSpan(span);
            mSpanCount--;
        }

        // Set text again.
        setText(mSpannableString);
    }

    //-- Accessors --//

    /**
     * Retrieves the number of spans applied to this SpannableString.
     *
     * @return The number of spans applied.
     */
    public int getSpanCount() {
        return mSpanCount;
    }

    /**
     * Retrieves all spans applied to the string.
     *
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

    /* Fade In */
    private static final Property<FadeInSpan, Integer> FADE_INT_PROPERTY
            = new Property<FadeInSpan, Integer>(Integer.class, "FADE_INT_PROPERTY") {

        @Override
        public void set(FadeInSpan span, Integer value) {
            span.setAlpha(value);
        }
        @Override
        public Integer get(FadeInSpan object) {
            return object.getAlpha();
        }
    };
}
