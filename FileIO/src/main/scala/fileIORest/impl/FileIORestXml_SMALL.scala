package fileIORest.impl

import java.io.PrintWriter
import java.io.File


class FileIORestXml_SMALL {


//TODO hier eine Option[String] übergeben

  def saveFromString(tableAsString: String): Unit = {
    val pw = new PrintWriter(new File("./savedAsString.txt"))
    pw.write(tableAsString)
    pw.close
  }

  //TODO mit einer Option machen

  def loadAsStringForSending: String = {
    val source = scala.io.Source.fromFile("./savedAsString.txt")
    val lines = try source.mkString finally source.close()
    lines
  }

}