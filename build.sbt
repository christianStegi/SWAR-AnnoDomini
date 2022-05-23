ThisBuild / organization := "de.htwg.swar"
ThisBuild / version      := "2.0"
ThisBuild / scalaVersion := "3.1.1"


lazy val FileIO = (project in file("FileIO"))
  .settings(libraryDependencies ++= commonDependencies)

lazy val root = (project in file("."))
  // .dependsOn(model, controller)
  .settings(libraryDependencies ++= commonDependencies)
  .settings(name := "AnnoDominiScala")
  // .aggregate(model, controller, FileIO)
    // .dependsOn(FileIO)
    // .aggregate(FileIO)


lazy val dependencies =
  new {
    val scalacticVersion = "3.2.12"
    val scalatestVersion = "3.2.12"
    val scalaXmlVersion = "2.1.0"
    val playJsonVersion = "2.10.0-RC5"
    val guiceVersion = "5.1.0"
    val scalaGuiceVersion = "5.0.2"
    val akkaVersion = "2.6.19"
    val old_akkaHttpVersion = "10.2.9"
    val new_akkaHttpVersion = "3.0.0-RC1"
    val akkaHttpVersion = old_akkaHttpVersion
    val slickVersion = "3.3.3"

    val scalactic = "org.scalactic" %% "scalactic" % scalacticVersion
    val scalatest = "org.scalatest" %% "scalatest" % scalatestVersion % "test"

    val scalaXML = "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion

    val playJson = ("com.typesafe.play" %% "play-json" % playJsonVersion).cross(CrossVersion.for3Use2_13)

    val guice = ("com.google.inject" % "guice" % guiceVersion)
    val scalaGuice = ("net.codingwell" %% "scala-guice" % scalaGuiceVersion).cross(CrossVersion.for3Use2_13)

    val akkaHttp = ("com.typesafe.akka" %% "akka-http" % akkaHttpVersion).cross(CrossVersion.for3Use2_13)
    val akkaActor = ("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion).cross(CrossVersion.for3Use2_13)
    val akkaStream = ("com.typesafe.akka" %% "akka-stream" % akkaVersion).cross(CrossVersion.for3Use2_13)

    val slick = "com.typesafe.slick" %% "slick" % slickVersion

  }


val commonDependencies = Seq(
ThisBuild / organization := "de.htwg.swar"
ThisBuild / version      := "2.0"
ThisBuild / scalaVersion := "3.1.1"


lazy val root = (project in file("."))
  .settings(libraryDependencies ++= commonDependencies)
  .settings(name := "AnnoDominiScala")
  .aggregate(model, controller, fileIO)

lazy val model = project in file("./model")
lazy val controller = project in file("./controller")
lazy val fileIO = project in file("./fileIO")


lazy val dependencies =
  new {
    val scalacticVersion = "3.2.12"
    val scalatestVersion = "3.2.12"
    val scalaXmlVersion = "2.1.0"
    val playJsonVersion = "2.10.0-RC5"
    val guiceVersion = "5.1.0"
    val scalaGuiceVersion = "5.0.2"
    val akkaVersion = "2.6.19"
    val akkaHttpVersion = "10.2.9"
    val slickVersion = "3.3.3"

    val scalactic = "org.scalactic" %% "scalactic" % scalacticVersion
    val scalatest = "org.scalatest" %% "scalatest" % scalatestVersion % "test"

    val scalaXML = "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion

    val playJson = ("com.typesafe.play" %% "play-json" % playJsonVersion).cross(CrossVersion.for3Use2_13)

    val guice = "com.google.inject" % "guice" % guiceVersion
    val scalaGuice = ("net.codingwell" %% "scala-guice" % scalaGuiceVersion).cross(CrossVersion.for3Use2_13)

    val akkaHttp = ("com.typesafe.akka" %% "akka-http" % akkaHttpVersion).cross(CrossVersion.for3Use2_13)
    val akkaActor = ("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion).cross(CrossVersion.for3Use2_13)
    val akkaStream = ("com.typesafe.akka" %% "akka-stream" % akkaVersion).cross(CrossVersion.for3Use2_13)

    val slick = "com.typesafe.slick" %% "slick" % slickVersion
  }

val commonDependencies = Seq(

  dependencies.scalactic,
  dependencies.scalatest,

  dependencies.scalaXML,

  dependencies.akkaHttp,
  dependencies.akkaActor,
  dependencies.akkaStream,

  dependencies.playJson,
  dependencies.guice,
  dependencies.scalaGuice,

  dependencies.slick,
)

//clears terminal output from sl4j logging error messages coming from sl4j not from our code
libraryDependencies += "org.slf4j" % "slf4j-nop" % "2.0.0-alpha7"