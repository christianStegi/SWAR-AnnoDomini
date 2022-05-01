ThisBuild / organization := "de.htwg.swar"
ThisBuild / version      := "2.0"
ThisBuild / scalaVersion := "3.1.1"

//version := "2.0"
//scalaVersion := "2.13.5"

lazy val root = (project in file("."))
  .settings(libraryDependencies ++= commonDependencies)
  .settings(name := "AnnoDominiScala")
  .aggregate(model, controller)


lazy val model = (project in file("./model"))

lazy val controller = (project in file("./controller"))


lazy val dependencies =
  new {
    val akkaVersion = "2.6.19"
    val akkaHttpVersion = "10.2.9"
    val scalacticVersion = "3.2.11"
    val scalatestVersion = "3.2.11"
    val scalaxmlVersion = "2.1.0"
    //val scalaxmlVersion = "2.0.1"
    //val playjsonVersion = "2.10.0-RC5"
    val playJsonVersion = "2.8.15"
    val guiceVersion = "5.1.0"
    val scalaGuiceVersion = "5.0.2"

    val scalactic = "org.scalactic" %% "scalactic" % scalacticVersion
    val scalatest = "org.scalatest" %% "scalatest" % scalatestVersion % "test"

    val scalaXML = "org.scala-lang.modules" %% "scala-xml" % scalaxmlVersion
    
    //val playJson = ("com.typesafe.play" %% "play-json" % playJsonVersion).cross(CrossVersion.for3Use2_13)

    //val guice = ("com.google.inject" % "guice" % guiceVersion).cross(CrossVersion.for3Use2_13)
    //val scalaGuice = ("net.codingwell" %% "scala-guice" % scalaGuiceVersion).cross(CrossVersion.for3Use2_13)
    
    val akka = ("com.typesafe.akka" %% "akka-http" % akkaHttpVersion).cross(CrossVersion.for3Use2_13)
    val akkaactor = ("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion).cross(CrossVersion.for3Use2_13)
    val akkastream = ("com.typesafe.akka" %% "akka-stream" % akkaVersion).cross(CrossVersion.for3Use2_13)
  }


val commonDependencies = Seq(
  dependencies.akka,
  dependencies.akkaactor,
  dependencies.akkastream,

  dependencies.scalactic,
  dependencies.scalatest,

  dependencies.scalaXML,

  //dependencies.playJson,
  //dependencies.guice,
  //dependencies.scalaGuice,
)



// for scala test
//libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

// google guice for Dependency Injection
//libraryDependencies += "com.google.inject" % "guice" % "5.1.0"
//libraryDependencies += "net.codingwell" %% "scala-guice" % "5.0.2"

// File IO:
// XML import:
//libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"
// JSon import:
//libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.15"

//libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC5"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.2"

libraryDependencies += ("net.codingwell" %% "scala-guice" % "5.0.2").cross(CrossVersion.for3Use2_13)
//"net.codingwell" %% "scala-guice" % "5.0.2"

libraryDependencies += "com.google.inject" % "guice" % "4.2.3"