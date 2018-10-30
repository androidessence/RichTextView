package com.androidessence.richtextview

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

//import com.androidessence.lib.CustomSpannableStringBuilder;
import com.androidessence.lib.RichTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val richTextView = findViewById(R.id.text_view) as RichTextView

        //richTextView.setSpannableString(new SpannableString(SAMPLE_STRING));
        //richTextView.formatNumberSpan();

        richTextView.text = SAMPLE_STRING
        richTextView.formatSpan(13, 18, RichTextView.FormatType.BOLD)
        richTextView.formatSpan(32, 39, RichTextView.FormatType.ITALIC)
        richTextView.formatSpan(53, 64, RichTextView.FormatType.UNDERLINE)
        richTextView.formatSpan(78, 92, RichTextView.FormatType.STRIKETHROUGH)
        richTextView.formatNumberSpan(1, 5)
        richTextView.formatBulletSpan(1, 5, 40, Color.GREEN)
        richTextView.formatSpan(106, 118, RichTextView.FormatType.SUPERSCRIPT)
        richTextView.formatSpan(132, 142, RichTextView.FormatType.SUBSCRIPT)
        richTextView.colorSpan(156, 161, RichTextView.ColorFormatType.FOREGROUND, Color.BLUE)
        // Test formatting to end of string.
        richTextView.colorSpan(180, 184, RichTextView.ColorFormatType.HIGHLIGHT, Color.RED)
        richTextView.formatImageSpan(206, 207, BitmapFactory.decodeResource(resources,
                R.mipmap.ic_launcher))
        richTextView.addHyperlinkToSpan(224, 233, "http://google.com")
        richTextView.formatScaleXSpan(2f,247, 253)
    }

    companion object {
        private val SAMPLE_STRING =
                "This text is bold\n" + // Bold = 13 - 18
                        "This text is italic.\n" + // Italic = 32 - 39
                        "This text is underlined.\n" + // Underlined = 53 - 64
                        "This text is strikethrough.\n" + // strikethrough = 78 - 92
                        "This text is superscript.\n" + // Superscript = 106 - 118
                        "This text is subscript.\n" + // Subscript = 132 - 142
                        "This text is blue.\n" + // Blue = 156 - 161
                        "This highlight is red.\n" + // Red = 180 - 184
                        "This line has a Image.\n" + // 207
                        "This line has a hyperlink.\n" +// Hyperlink = 223 - 233
                        "This text is scaled." // scaled = 247 - 253
    }
}
