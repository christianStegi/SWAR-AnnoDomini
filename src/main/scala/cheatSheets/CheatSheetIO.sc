import scala.io.Source

/*
This sheet is supposed to help illustrate I/O methods.
 */

// first of, we need the location ot the file,
// and we should get it in a way, that is
val path = System.getProperty("user.dir")
// I wanted to use the method above to get the current path.
// this works in the main, but this worksheet gives me an entirely different
// path, I am assuming this is because of the way workseets work?
// as a temp solution I wrote down my absolute path, this will only work on my pc
val curPath = "C:/Users/Sarah/IdeaProjects/AnnoDomini/src/main/scala/cheatSheets/"

val source = Source.fromFile(curPath + "fiveNums.txt")  // this is a BufferedSource Iterator
val lines = source.getLines  // This is a String Iterator
val nums = lines.filter(_.nonEmpty).map(_.toInt).sum // And this calculates the average num of the numbers in fiveNums.txt

source.close()
