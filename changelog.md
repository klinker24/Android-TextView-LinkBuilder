## Changelog

### Version 2.0.1:
- Kotlin cleanup

### Version 2.0.0:
- Built on Kotlin
- Extension method support for `TextView#applyLinks` to avoid boilerplate of creating the `LinkBuilder`

### Version 1.6.0:
- Remove `Spannable` that would be consumed by other links so they are fully clickable

### Version 1.6.0:
- Improvements around support for regular expression matching

### Version 1.5.2:
- Don't default the text color of highlighted links to light blue

### Version 1.5.1:
- If a link already exists on a block of text, don't try to put another link over the top of it

### Version 1.5.0:
- Add ability to only search for the first occurrence of a pattern

### Version 1.4.0:
- Add the ability to customize the text color of links when they are being touched/highlighted

### Version 1.3.3:
- Update example with Material Design
- Update dependencies and build tools for SDK 25

### Version 1.3.2:
- Convert to using `CharSequence`

### Version 1.3.1:
- Add a method to make links bold

### Version 1.3.0:
- Added ability to work with `ListView.OnItemClickListener` through the use of `LinkConsumableTextView`

### Version 1.2.0:
- Add a set typeface method to `Link`

### Version 1.1.0:
- Cut to `LinkBuilder.from(String)` and `LinkBuilder.on(TextView)` to create an instance of `LinkBuilder`
- Allow `LinkBuilder` to return a `CharSequence` for the links without setting them to a `TextView` automatically. (Use `LinkBuilder.from(String).build()`)

### Version 1.0.6:
- Allow for styling the default color of the links from activity theme
- Don't allow null links when building the `SpannableString`
- Find all links of the same text when using a regular expression

### Version 1.0.5:
- If the link contained the last character of the line, clicking the empty space at the end of the line would also click the link. This removes that bug.

### Version 1.0.4:
- Add prepended and appended text to links
- Fix: if no matches are found when linking regular expressions, the text would not be shown at all
- Convert line endings from CRLF to LF for git repository
- Add changelog
- Add release tags

### Version 1.0.3:
- Use the `TextView` to preform haptic feedback instead of `Vibrator` class.
- Remove vibrate permission

### Version 1.0.2:
- Chain the `LinkBuilder` class for easier and more structured implementation

### Version 1.0.1:
- Fix incorrect package name
- Upload to maven central

### Version 1.0.0:
- Initial release
