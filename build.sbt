ThisBuild / organization := "de.htwg.swar"
ThisBuild / version      := "2.0"
ThisBuild / scalaVersion := "3.1.1"


lazy val root = (project in file("."))
  .settings(libraryDependencies ++= commonDependencies)
  .settings(name := "AnnoDominiScala")
  .aggregate(model, controller)


lazy val model = (project in file("./model"))

lazy val controller = (project in file("./controller"))


lazy val dependencies =
  new {
    val scalacticVersion = "3.2.11"
    val scalatestVersion = "3.2.11"
    val scalaXmlVersion = "2.1.0"
    val playJsonVersion = "2.10.0-RC5"
    val guiceVersion = "5.1.0"
    val scalaGuiceVersion = "5.0.2"
    val akkaVersion = "2.6.19"
    val akkaHttpVersion = "10.2.9"

    val scalactic = "org.scalactic" %% "scalactic" % scalacticVersion
    val scalatest = "org.scalatest" %% "scalatest" % scalatestVersion % "test"

    val scalaXML = "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion
    
    val playJson = ("com.typesafe.play" %% "play-json" % playJsonVersion).cross(CrossVersion.for3Use2_13)

    val guice = ("com.google.inject" % "guice" % guiceVersion)
    val scalaGuice = ("net.codingwell" %% "scala-guice" % scalaGuiceVersion).cross(CrossVersion.for3Use2_13)
    
    val akka = ("com.typesafe.akka" %% "akka-http" % akkaHttpVersion).cross(CrossVersion.for3Use2_13)
    val akkaActor = ("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion).cross(CrossVersion.for3Use2_13)
    val akkaStream = ("com.typesafe.akka" %% "akka-stream" % akkaVersion).cross(CrossVersion.for3Use2_13)
    val akkaXML = ("com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion).cross(CrossVersion.for3Use2_13)
  }


val commonDependencies = Seq(
  dependencies.akka,
  dependencies.akkaActor,
  dependencies.akkaStream,
  dependencies.akkaXML,

  dependencies.scalactic,
  dependencies.scalatest,

  dependencies.scalaXML,

  dependencies.playJson,
  dependencies.guice,
  dependencies.scalaGuice,
)
