
// defining

val array1 = Array(1,7,1,4,5)  // creates an array as described in ()
// creates an array, with the size of 5 and fills it with 9
val array2 = Array.fill(5)(9) //Array(9, 9, 9, 9, 9)
// creates an array, with the size of 6, and fills it with the iteration of i starting with 0
val array3 = Array.tabulate(6)(i => i)  // Array(0, 1, 2, 3, 4, 5)
// creates an array, with the size of 5 and fills it with the i*2
val array4 = Array.tabulate(5)(i => 2*i)  // Array(0,2,4,6,8)

// the folowing methods are the same for list and arrays
// also none of them actually change the array/list itself,
// they just return a changed version of the array/list

// drops the first x values of a array or a list
array1.drop(3)  // returns Array(4, 5)
array1.drop(2)  // retruns Array(1,4,5)
array1 // the array itself doesn't actually change!!!
array1.takeRight(3)  // this is the same as array1.drop(2)

// tail, returns everything after the first element
array1.tail // Array(7,1,4,5)
// init is the oposite of tail, it gives you everytihng bevore the last element
array1.init  // Array(1,7,1,4)

array1.head  // returns first element of the array/list
array1.last  // returns the last element of the array/list

// return specific subarray/list of an array/list:
array1.slice(1,3)  // Array(7,1)
// start is inclusive, end is exclusive, so this returns positions 1 and 2

// splits the array/list into tuple with two arrays/list
// the parameter stands for the amount of vaul array/list
array1.splitAt(0)  // (empty array, array1)
array1.splitAt(2)  // (Array(1,7), Array(1,4,5))
array1.splitAt(3)  // (Array(1,7,1), Array(4,5))

// get the fist x elements of a array/list
// counterpart to drop
array1.take(2)  // Array(1,7)
array1.take(3)  // Array(1,7,1)
// this is the same as array1.take(2)
array1.dropRight(3)

// Boolean methods
//checks wether a single value is contained
array1.contains(1)  // true
array1.contains(11)  // false

// checks whether the array/list ends with another collection
array1.endsWith(Array(1, 4, 5)) // true
array1.endsWith(List(1, 4, 5)) // true
array1.endsWith(Array(1, 4))  // false
array1.endsWith(Array(5))   // true

// checks whether the array/list starts with another collection
array1.startsWith(Array(1, 7, 1))  //true
array1.startsWith(List(1, 7, 1))  // true
array1.startsWith(Array(1, 1, 7))  // false

// check wether a list/array is empty/not empty
array1.isEmpty
!array1.isEmpty
array1.nonEmpty

// searches for the index of a specific entry
array1.indexOf(1)  // returns 0, the index of first 1
array1.indexOf(8)  // returns -1, because there is no 8 in the array
array1.lastIndexOf(1)  // returns 2, the index of the last 1
array1.indexOf(1, 1)  // returns 2, the index of the first 1 at or after index 1
array1.indexOf(1, 2)  // returns 2, the index of the first 1 at or after index 1
array1.indexOf(1,3)// returns -1, because there is no 1 after index 3

// removes the values, that are the same as the given values
array1.diff(Array(1, 7)) // Array(1, 4, 5)
array1.diff(Array(7, 5)) // Array(1, 1, 4)

// makes sure any value only exists once
array1.distinct  // Array(1,7,4,5)

// turns all the values of an array/list into a single string
array1.mkString  // no separation
array1.mkString(",")  // separated by comma
array1.mkString(" ")  // separated by space

// takes a subset of the list/array and replaces it with something else
array1.patch(2, Nil, 4)  // start at index 2, replace with Nil, do that for 4 values
array1.patch(2, Nil, 1)
array1.patch(3, List(8), 1)  // Array(1, 7, 1, 8, 5), replaced value at 4 with 8
array1.patch(3, List(8), 2)  // Array(1, 7, 1, 8), replaced value at 3 with 8 and removed the values after that
array1.patch(3, List(8, 9), 1)  // Array(1, 7, 1, 8, 9, 5)
array1.patch(3, List(8, 9), 2)  // Array(1, 7, 1, 8, 9)
array1
array1.patch(0, List(10), 0)


// reverse a list/array
array1.reverse

// combine the values of two lists/arrays into a new array of tuples, made with the values of the old arrays/list
// just look at the return value, it's hard to explain
array1  // Array(1, 7, 1, 4, 5)
array3  // Array(0, 1, 2, 3, 4, 5)
array1.zip(array3)  // Array((1,0), (7,1), (1,2), (4,3), (5,4))
array3.zip(array1)  // Array((0,1), (1,7), (2,1), (3,4), (4,5))
// which comes fist influences the tuple and the type

// also does the tuple thing like zip,
// but this time the second value is the index of the value
array1.zipWithIndex  // Array((1,0), (7,1), (1,2), (4,3), (5,4))
array3.zipWithIndex  // Array((0,0), (1,1), (2,2), (3,3), (4,4), (5,5))

// math with lists/arrays:
array1.max
array1.min
array1.sum  // adds all values, only works with numerics!
array1.product  // multiplies all values, only works with numerics!
