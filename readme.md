[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RichTextView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3483)

RichTextView Library
=============

This library is an enhancement to the TextView component that allows you to style various spans of text inside the view.


Usage
-----

To have access to the library, add the following dependency to your build.gradle:

```java
	compile 'com.androidessence.lib:richtextview:1.0.2'
```

Format Types
------------

You can apply any combination of the following formats on a span of text:

- Bold
- Italic
- Underline
- Strikethrough
- Superscript
- Subscript

To use these, use one of the following formatSpan methods:

- `formatSpan(int start, int end, FormatType formatType)`
	- This method is used to apply a certain format to a span of text in the RichTextView. Start (inclusive) and end (exclusive) are the indexes of the start and end characters to format.
- `formatSpan(int start, int end, EnumSet<FormatType> formatTypes)`
	- This method has the same function as the one above, but allows you to apply a number of FormatTypes to this span of text.
- `formatNumberSpan(int startline, int endline)`
	- This method is used to apply NumberSpan based on the start and end line specified.
- `formatBulletSpan(int startline, int endline)`
	- This method is used to apply Bullets based on start and end lines.
- `formatImageSpan(int start, int end, Bitmap bitmap)`
	- This method is used to apply ImageSpan based on the start and end line specified along with a bitmap specified.
- `formatFadeInSpan(int startIndex, int endIndex)`
	- This method fades in text that starts with startIndex and ends with endIndex.


Color Format Types
------------------

You can apply the following color formats on a span of text:

- Foreground (the text color)
- Highlight (the background color of the span)

To use these formats, use the following method:
- `colorSpan(int start, int end, FormatType formatType, int color)`
	- This method functions just as the ones above it, the only difference is that it also requires a color to apply to the span. For example, if you wanted to make the first four characters of text blue, you would use `colorSpan(0, 5, FormatType.FOREGROUND, Color.BLUE);`

Sample
-----

Below is a sample of all the possible formats you can apply to a RichTextView. In this example it is all one string, formatted eight different ways.

<img src='https://github.com/raghunandankavi2010/RichTextView/blob/master/Screenshot%20(28-Nov-2016%2011-19-15%20AM).png' width='400' height='640' />

Credits & Contact
-----------------

This library was created by [Adam McNeilly](http://adammcneilly.com) with special help from:
 - [Maurício Pessoa](https://github.com/Mauker1)
 - [Trevor Elkins](http://trevore.com)
 - [Raghunandan Kavi](https://github.com/raghunandankavi2010)

It is also released under [Android Essence blog](http://androidessence.com/).

License
-------

The RichTextView library is available under the [MIT License](https://opensource.org/licenses/MIT). You are free to modify and enhance it in any way. If you submit a pull request, please add your name into the credits section! :)