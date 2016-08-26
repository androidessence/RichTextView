package com.androidessence.richtextview;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;

//import com.androidessence.lib.CustomSpannableStringBuilder;
import com.androidessence.lib.RichTextView;

import java.util.EnumSet;

public class MainActivity extends AppCompatActivity {
    private static final String SAMPLE_STRING =
            "This text is bold.\n" + // Bold = 13 - 18
                    "This text is italic.\n" + // Italic = 32 - 39
                    "This text is underlined.\n" + // Underlined = 53 - 64
                    "This text is strikethrough.\n" + // strikethrough = 78 - 92
                    "This text is superscript.\n" + // Superscript = 106 - 118
                    "This text is subscript.\n" + // Subscript = 132 - 142
                    "This text is blue.\n" + // Blue = 156 - 161
                    "This highlight is red.\n" + // Red = 180 - 184
                    "This line has a Image."; // 207


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RichTextView richTextView = (RichTextView) findViewById(R.id.text_view);

        richTextView.setText(SAMPLE_STRING);
		
        richTextView.formatSpan(13, 18, RichTextView.FormatType.BOLD);
        richTextView.formatSpan(32, 39, RichTextView.FormatType.ITALIC);
        richTextView.formatSpan(53, 64, RichTextView.FormatType.UNDERLINE);
        richTextView.formatSpan(78, 92, RichTextView.FormatType.STRIKETHROUGH);
        richTextView.formatNumberSpan(1, 5);
        richTextView.formatSpan(106, 118, RichTextView.FormatType.SUPERSCRIPT);
        richTextView.formatSpan(132, 142, RichTextView.FormatType.SUBSCRIPT);
        richTextView.colorSpan(156, 161, RichTextView.ColorFormatType.FOREGROUND, Color.BLUE);
        // Test formatting to end of string.
        richTextView.colorSpan(180, 184, RichTextView.ColorFormatType.HIGHLIGHT, Color.RED);
        richTextView.formatImageSpan(206,207, BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));

        //richTextView.clearSpans();



    }
}
