# Hex Scanner

## Purpose

This Hex Scanner is a quick (emphasis on quick) solution to finding all the hex codes in a used in a file. Additionally, it outputs the line numbers that you can find that color.

## Limitations

- The program only finds pure, 6 digit hex codes. Hex codes with Alpha values will be cut off to include only the 6, and hex code shorthands like #000 for black just won't be shown. The algorithm as is just checks for the characters ANSCII value, looking at the 6 characters after the hashtag. Lastly, if there are multiple hex codes on one line, only the first will be selected.

## Enhancing the Algorithm

If I feel like adding more to this quick build at a later data, then I'll implement the following features:

- Recognizing hex code shorthands
- Search for multiple hex colors on the same line
- Output to txt, json, or xlsx
- Format hexadecimal output (all caps, lowercase, etc)
- Sorting based on color
