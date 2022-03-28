/*
This Worksheet is supposed to collect and explain several String operations and give examples for their use
This is supposed to be a quick lookup if one of us has to deal with Strings
This does have no effect on the game itself
 */

// some basics:

val string = "Hello World"

string.length  // 11
string.isEmpty  // false
string.nonEmpty  // true

// getting a substring
string.substring(6)  // return everything beginning at position 6
// beginning parameter is inclusiv, end parameter is exclusiv
string.substring(6,6)  // return empty string
string.substring(6, 7)  // return letter/symbol at pos 6
string.substring(6, 9)  // return symbols from position 6 to 8

// check for letters/symbols
string.contains('W')  // true
string.contains('w')  // false
string.contains('i' )  // false

// give index of char
string.indexOf('o')
string.indexOf('o', 0)
string.indexOf('o', 5)
string.indexOf('o', string.indexOf('o') + 1)
string.indexOf('o', 8)
// This is usually combined with substring to properly split a string
val name = "Max Mustermann"
val surname = name.substring(name.indexOf(' ') + 1)
val time = "10:30"
val hours = time.substring(0, time.indexOf(':')).toInt

// easier spliting:
name.split(" +")  // takes a regex as parameter!
time.split(":")
//returns an Array of substings,
// spiltted at the given symbol/regex

// trim: remove spaces at the beginning and end of a string
val spaces = "     Hi there!     "
spaces.trim()
name.substring(name.indexOf(' ')).trim // removes the space from the beginning without the need of adapting the index

// String Collections:
// String is a subset of Collections, therefore the Collectionmethods explained in Array and List also work on Strings:
// count vowels:
string.count(c => "aeiou".contains(c))
