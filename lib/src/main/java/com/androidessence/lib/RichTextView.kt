package com.androidessence.lib

import android.animation.IntEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.util.AttributeSet
import android.util.Property
import android.widget.TextView
import java.util.*

/**
 * Rich TextView Component that is an enhanced version of a TextView with certain styling.
 *
 *
 * Created by adammcneilly on 4/1/16.
 */
class RichTextView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(mContext, attrs) {

    /**
     * The spannable string to display in this TextView.
     */
    private var mSpannableString: SpannableString? = null

    /**
     * The number of spans applied to this SpannableString.
     */
    var spanCount: Int = 0
        private set

    init {
        this.spanCount = 0

        initStyle(attrs, defStyleAttr)
    }

    private fun initStyle(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RichTextView, defStyleAttr, 0)

        //TODO: Implement special attributes.
        typedArray?.recycle()
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        // Set our spannable string.
        mSpannableString = SpannableString(text)
        super.setText(mSpannableString, type)
    }

    /**
     * Add a bullet at the start fo each line. You can specify start and endline
     *
     * @param start      The index at which the text starts to fade.
     * @param end        The index at which the text fade ends.
     * @param duration   The duration for the text fade.
     */
    fun formatFadeInSpan(start: Int, end: Int, duration: Int) {

        spanCount++
        val span = FadeInSpan()

        mSpannableString!!.setSpan(span, start, end, 0)

        val objectAnimator = ObjectAnimator.ofInt(
                span, FADE_INT_PROPERTY, 0, 255)
        objectAnimator.setEvaluator(IntEvaluator())
        objectAnimator.addUpdateListener { text = mSpannableString }
        objectAnimator.duration = duration.toLong()
        objectAnimator.start()
    }

    /**
     * Add a bullet at the start fo each line. You can specify start and endline
     *
     * @param startline      The start line at which bullet is shown.
     * @param endline        The end line at which bullet is shown.
     */
    fun formatBulletSpan(startline: Int, endline: Int) {

        spanCount++
        val splitter = mSpannableString!!.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        var start = 0

        splitter.indices
                .asSequence()
                .filter {
                    splitter[it] != "" && splitter[it] != "\n"
                }
                .forEach {
                    if (it >= startline - 1 && it < endline) {

                        mSpannableString?.setSpan(com.androidessence.lib.BulletSpan(100, false), start, start + 1, 0)
                        start += splitter[it].length + 1
                    } else {
                        start += splitter[it].length + 1
                    }
                }

        text = mSpannableString
    }

    /**
     * Add a bullet at the start fo each line. You can specify start and endline
     *
     * @param startline      The start line at which number is shown.
     * @param endline        The end line at which number is shown.
     */
    fun formatNumberSpan(startline: Int, endline: Int) {

        spanCount++
        val splitter = mSpannableString!!.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        var start = 0
        var index = 1

        splitter.indices
                .asSequence()
                .filter {
                    splitter[it] != "" && splitter[it] != "\n"
                }
                .forEach {
                    if (it >= startline - 1 && it < endline) {
                        mSpannableString!!.setSpan(NumberSpan(index++, 100, false, textSize), start, start + 1, 0)
                        start += splitter[it].length + 1
                    } else {
                        start += splitter[it].length + 1
                    }
                }

        text = mSpannableString
    }

    /**
     * Add a Image at the specified index
     *
     * @param start    The start index where image is shown.
     * @param end      The end index where image is shown.
     */
    fun formatImageSpan(start: Int, end: Int, drawable: Bitmap) {
        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if (start < 0 || start >= (mSpannableString?.length ?: 0)) {
            throw IllegalArgumentException("Invalid start index.")
        } else if (end < start || end > (mSpannableString?.length ?: 0)) {
            throw IllegalArgumentException("Invalid end index.")
        }

        // Add span
        spanCount++
        mSpannableString?.setSpan(ImageSpan(context, drawable), start, end, 0)

        // Set text
        text = mSpannableString
    }

    /**
     * Formats a Span of text in the text view. This method was added as a convenience method
     * so the user doesn't have to use EnumSet for one item.
     *
     * @param start      The index of the first character to span.
     * @param end        The index of the last character to span.
     * @param formatType The format to apply to this span.
     */
    fun formatSpan(start: Int, end: Int, formatType: FormatType) {
        // Call the other method as if this were an EnumSet.
        formatSpan(start, end, EnumSet.of(formatType))
    }

    /**
     * Formats a Span of text in the text view.
     *
     * @param start       The index of the first character to span.
     * @param end         The index of the last character to span.
     * @param formatTypes The formats to apply to this span.
     */
    fun formatSpan(start: Int, end: Int, formatTypes: EnumSet<FormatType>) {


        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if (start < 0 || start >= (mSpannableString?.length ?: 0)) {
            throw IllegalArgumentException("Invalid start index.")
        } else if (end < start || end > (mSpannableString?.length ?: 0)) {
            throw IllegalArgumentException("Invalid end index.")
        }
        // There is no need to consider a null FormatType. Since we have two method headers of (int, int, type)
        // The compiler will claim that `formatSpan(int, int, null);` call is ambiguous.

        spanCount += formatTypes.size

        // Create span to be applied - Default to normal.
        formatTypes.forEach { mSpannableString?.setSpan(it.span, start, end, 0) }

        text = mSpannableString
    }

    /**
     * Colors a portion of the string.
     *
     * @param start      The index of the first character to color.
     * @param end        The index of the last character to color..
     * @param formatType The type of format to apply to this span.
     * @param color      The color to apply to this substring.
     */
    fun colorSpan(start: Int, end: Int, formatType: ColorFormatType?, color: Int) {
        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if (start < 0 || start >= (mSpannableString?.length ?: 0)) {
            throw IllegalArgumentException("Invalid start index.")
        } else if (end < start || end > (mSpannableString?.length ?: 0)) {
            throw IllegalArgumentException("Invalid end index.")
        } else if (formatType == null) {
            throw IllegalArgumentException("Invalid FormatType.")
        }

        // Add span
        spanCount++
        mSpannableString!!.setSpan(formatType.getSpan(color), start, end, 0)

        // Set text
        text = mSpannableString
    }

    /**
     * Adds a hyperlink to a portion of the string.
     *
     * @param start      The index of the first character of the link.
     * @param end        The index of the last character  of the link.
     * @param url        The URL that the hyperlink points to.
     */
    fun addHyperlinkToSpan(start: Int, end: Int, url: String) {
        // If the start index is less than 0 or greater than/equal to the length of the string, it is invalid.
        // If the end index is less than start or greater than the string length, it is invalid.
        if (start < 0 || start >= (mSpannableString?.length ?: 0)) {
            throw IllegalArgumentException("Invalid start index.")
        } else if (end < start || end > (mSpannableString?.length ?: 0)) {
            throw IllegalArgumentException("Invalid end index.")
        }

        // Add span
        spanCount++
        this.movementMethod = LinkMovementMethod.getInstance()

        val urlSpan = URLSpan(url)
        mSpannableString!!.setSpan(urlSpan, start, end, 0)

        // Set text
        text = mSpannableString
    }

    /**
     * Clears all spans applied to our SpannableString.
     */
    fun clearSpans() {
        // Get spans
        val spans = mSpannableString?.getSpans(0, spanCount, Any::class.java)

        // Loop through and remove each one.
        spans?.forEach {
            mSpannableString?.removeSpan(it)
            spanCount--
        }

        // Set text again.
        text = mSpannableString
    }

    /**
     * Retrieves all spans applied to the string.
     *
     * @return An array of object representing the types of spans applied.
     */
    val spans: Array<out Any>?
        get() = mSpannableString?.getSpans(0, spanCount, Any::class.java)


    //-- Inner classes --//

    /**
     * An enumeration of various ways to format the text.
     */
    enum class FormatType {
        NONE {
            override val span: Any
                get() = StyleSpan(Typeface.NORMAL)
        },

        BOLD {
            override val span: Any
                get() = StyleSpan(Typeface.BOLD)
        },

        ITALIC {
            override val span: Any
                get() = StyleSpan(Typeface.ITALIC)
        },

        UNDERLINE {
            override val span: Any
                get() = UnderlineSpan()
        },

        STRIKETHROUGH {
            override val span: Any
                get() = StrikethroughSpan()
        },

        SUPERSCRIPT {
            override val span: Any
                get() = SuperscriptSpan()
        },

        SUBSCRIPT {
            override val span: Any
                get() = SubscriptSpan()
        };

        abstract val span: Any
    }

    /**
     * An enumeration of various ways to color the text.
     */
    enum class ColorFormatType {
        FOREGROUND {
            override fun getSpan(color: Int): Any {
                return ForegroundColorSpan(color)
            }
        },

        HIGHLIGHT {
            override fun getSpan(color: Int): Any {
                return BackgroundColorSpan(color)
            }
        };

        abstract fun getSpan(color: Int): Any
    }

    companion object {
        /* Fade In */
        private val FADE_INT_PROPERTY = object : Property<FadeInSpan, Int>(Int::class.java, "FADE_INT_PROPERTY") {

            override fun set(span: FadeInSpan, value: Int?) {
                span.alpha = value ?: 0
            }

            override fun get(`object`: FadeInSpan): Int {
                return `object`.alpha
            }
        }
    }
}
