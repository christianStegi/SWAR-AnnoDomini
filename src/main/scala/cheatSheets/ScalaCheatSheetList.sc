/*
This Worksheet is supposed to collect and explain several List operations and give examples for their use
This is supposed to be a quick lookup if one of us has to deal with Lists
This does have no effect on the game itself
 */

// List declaration
val list = List(1, 2, 3, 8, 1, 6, 4)
val list2 = 1::2::3::4::Nil
val list3 = List.fill(5)(0)
val list4 = list2:::list

// basic Functions
list.head // 1
list.tail // List(2, 3, 8, 1, 6, 4)
list.last // 4
list.drop(3) // get list without the first n elements
list.drop(1) // the same as tail
list.dropRight(3) // get list without the last n elements
list.dropRight(1) // the list without last element


list.length
list.size

// lists are inmutable:
6::list  // puts 6 at the head, but change is only temporarily
list  // is the same as in declaration, because lists are immutable
list(2)  // returns 3, like Array
// list(2) = 3  // throws error, because List is immutable

list match{
  case Nil => 1    // match is basically a switch case
  case h::t => t  // automatically recognizes h as head and t as tail
}

// turn a list into an array
val array = Array.fill(list.length)(0)
// overides array from index 2 on, starts from start of the list
list.copyToArray(array, 2) // returns amount of copied elements
array
list.copyToArray(array, 1,2) // overides array from index 1 to 2, starts from start of the list
array
list.copyToArray(array) // overrides full array.
array

// there are several methods that can be used on both list and array
// I put the examples for those into the Array CheatSheet, please
// check it for further information

list.zipWithIndex
list

// Higher order methods: (Those also work on arrays!!!!)
// use the given function on every element of the list
list
list.map(_*2)
list.map(i => i*2)
list.map(i => i / 0.5)
list.map(i => "hi " * i)
list.map(i => Array.fill(i)(i))  // creates a list of arrays

// flat map, its like map but flat
list.flatMap(i => Array.fill(i)(i))  // creates a list of int, instead of a list of arrays
// list.flatMap(_*2)  // creates an error,
// because flatmap only deals with functions that return collections


// filter out certain elements:
list.filter(i => i%2==0)  // gives back all even numbers
list.filter(_%2==0)
list.filter(i => i%2==1)  // gives back all uneven numbers

list.filterNot(i => i%2==0) // gibes back all uneven numbers

// combine filter and filterNot:
list.partition(_%2==0)  // (List[Int], List[Int]) = (List(2, 8),List(1, 3))
// this returns a tuple of two collections,
// the first has all the values that applied to the filter
// the second has the values that didn't



// of course they can be combined!!!
list.filter(i => i%2==0).map(_*2)
list.filter(i => i%2==0).map(_*2).filter(i => i <= 8)

// count elements that apply to given filter
list.count(_%2==0)
list.count(_==0)
list3.count(_==0)

// get rid of all elements as long as the filter applies
list.dropWhile(_<2)
list.dropWhile(_<1)
list.dropWhile(_<8)
list.dropWhile(_==2)
list.dropWhile(_==1)
list3.dropWhile(_==0)

// we also have the same for take:
list
list.filter(_<5)
list.takeWhile(_<5)
list.takeWhile(_<1)

// check for existence of certain element type
// TODO: check how to change warning settings, I want to ignore the following two warnings...
//noinspection ExistsEquals
list.exists(_==3)  // true
//noinspection ExistsEquals
list.exists(_==4)  // false
list.exists(_%2==0)  // true
list.exists(_%8==0)  // true
list.exists(_%5==0)  // true

// check if a ruel applies to all elements
list.forall(_<10)  // true
list.forall(_<5)  // false

// apply function for each element, without giving a return value
// usually used for functions with sideeffects.
list.foreach(println)

// another index method
// works with filter,
// but will once again only give the index of the first correct value
list.indexWhere(_%2==0)  // returns 1
list3.indexWhere(_==0)  // returns 0

// reduceLeft
//noinspection SimplifiableFoldOrReduce
list.reduceLeft(_+_)  // would do the same as sum
list.sum
list.reduceLeft(_+_)
list.reduceLeft((a,b) => {println(a+", "+b); a+b})
// this still sums up all the values, but also prints the in between steps
list.reduceRight((a,b) => {println(a+", "+b); a+b})
// same as above, but works in the opposite direction)
// same example with subtraction, but this time the outcome will change!
list.reduceLeft((a,b) => {println(a+", "+b); a-b})  // -23
list.reduceRight((a,b) => {println(a+", "+b); a-b})  // -7

// fold: does the same as reduce, but it will
// take the value it gives as the first value of the function
//noinspection SimplifiableFoldOrReduce
list.foldLeft(0)(_+_)  // 25, the same as reduceLeft(_+_)
list.foldLeft(1)(_+_)  // 26, because it does the above math but starts with one as first value in addition to what the List has
list.foldLeft(10)(_+_)  // 35

list.foldLeft("")(_+_) // "1238164", turns the list into a string because of the empty string given
list.foldLeft(0.0)(_+_)  // 25.0

// TODO: add Iterator examples
// TODO: maybe move those into their own cheat sheet, idk.