## Changelog

### Version 1.0.6:
- Allow for styling the default color of the links from activity theme
- Don't allow null links when building the SpannableString
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
- Use the TextView to preform haptic feedback instead of Vibrator class.
- Remove vibrate permission

### Version 1.0.2:
- Chain the LinkBuilder class for easier and more structured implementation

### Version 1.0.1:
- Fix incorrect package name
- Upload to maven central

### Version 1.0.0:
- Initial release
