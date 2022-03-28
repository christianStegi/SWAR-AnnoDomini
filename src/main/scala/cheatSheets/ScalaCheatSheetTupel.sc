/*
A Tuple is a sequence of elements of different type
it can be used to return several different types from a function
or to assign two values in one step
 */

val tuple1 = (2, 2.0, "two")
tuple1._1  // 2
tuple1._2  // 2.0
tuple1._3  // two

val tuple2 = (4, 7)

// tuples can also contain tuples

val tuple3 = (tuple1, tuple2)
tuple3._1  // tuple1
tuple3._2  // tuple2

tuple3._1._2  // 2.0
tuple3._2._1  // 4