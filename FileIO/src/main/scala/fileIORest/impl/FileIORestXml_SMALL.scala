package fileIORest.Impl

import java.io.PrintWriter
import java.io.File


class FileIORestXml_SMALL {



  def saveFromString(tableAsString: String): Unit = {
    val pw = new PrintWriter(new File("./savedAsString.txt"))
    pw.write(tableAsString)
    pw.close
  }


  def loadAsStringForSending: String = {
    val source = scala.io.Source.fromFile("./savedAsString.txt")
    val lines = try source.mkString finally source.close()
    lines
  }

}