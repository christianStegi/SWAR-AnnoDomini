name := "AnnoDominiScala"

version := "1.0"

scalaVersion := "3.1.1"
//scalaVersion := "2.13.5"

// for scala test
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

// google guice for Dependency Injection
/*
libraryDependencies += "com.google.inject" % "guice" % "5.1.0"
libraryDependencies += "net.codingwell" %% "scala-guice" % "5.0."
*/

// File IO:
// XML import:
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"
// JSon import:
// libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.15