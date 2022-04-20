ThisBuild / organization := "de.htwg.swar"
ThisBuild / version      := "2.0"
ThisBuild / scalaVersion := "3.1.1"

//version := "2.0"
//scalaVersion := "2.13.5"

lazy val model = (project in file("model"))

lazy val controller = (project in file("controller"))
  .dependsOn(model)

lazy val root = (project in file("."))
  .dependsOn(model, controller)
  .aggregate(model, controller)
  .settings(name := "AnnoDominiScala")


//lazy val model = (project in file("./model"))

//lazy val controller = (project in file("./controller"))



// for scala test
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

// google guice for Dependency Injection
//libraryDependencies += "com.google.inject" % "guice" % "5.1.0"
//libraryDependencies += "net.codingwell" %% "scala-guice" % "5.0."

// File IO:
// XML import:
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"
// JSon import:
//libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.15"

