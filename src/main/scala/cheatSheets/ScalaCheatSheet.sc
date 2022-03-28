/*
  This Worksheet is supposed to provide examples for basic Scala operations and some explanations for them
 */
var mutable = 2 // Value can be reasigned
mutable = 5 // is allowed

val inmutable = 3
// inmutable = 4  // causes error

// Functions
def sum (x:Double, y:Double) = x + y
sum(2, 3)
sum(8, 9.5)
// Overwriting seems to work, but not overloading
// which is weird, because method overloading is a thing in scala
def ssum(x:Double) = x + x
ssum(3)
ssum(8)
// sum(2.5, 3.4) throws error, because of to many arguments

// lambda functions:
val square = (x:Double) => x * x
square(3)
square(4)
// I can use functions as if they where values
val double = (_:Double)*2  // _ is the wildcard character in scala
double(1)
double(2)

// higher functions:
// allow to combine values with functions
// are kinda complicated but quite usefull once you get them

def combine(x:Double, y:Double, z:Double, f:(Double, Double)=>Double)
= f(f(x,y), z)

combine(1, 2, 4, sum)  // f is a predefined func
combine(1,2,4, (x,y)=>x+y)  // f is a newly defined func
combine(1,2,4, _+_)  // defining a new f with wildcards
// the three instanaces above do the same thing.
combine(1,2,4,_*_)