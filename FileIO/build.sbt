ThisBuild / organization := "de.htwg.swar"
ThisBuild / version      := "2.0"
ThisBuild / scalaVersion := "3.1.1"


lazy val FileIO = (project in file("."))
  .settings(libraryDependencies ++= commonDependencies)
  .settings(name := "FileIO")
//   //  .dependsOn(model)
//   //  .dependsOn(root)


lazy val dependencies =
  new {
    val scalacticVersion = "3.2.11"
    val scalatestVersion = "3.2.11"
    val scalaXmlVersion = "2.1.0"
    val playJsonVersion = "2.10.0-RC5"
    val guiceVersion = "5.1.0"
    val scalaGuiceVersion = "5.0.2"
    val akkaVersion = "2.6.19"
    val old_akkaHttpVersion = "10.2.9"
    val new_akkaHttpVersion = "3.0.0-RC1"
    val akkaHttpVersion = old_akkaHttpVersion
    val loggerStuffVersion = "2.0.0-alpha7"


    val scalactic = "org.scalactic" %% "scalactic" % scalacticVersion
    val scalatest = "org.scalatest" %% "scalatest" % scalatestVersion % "test"

    val scalaXML = "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion

    val playJson = ("com.typesafe.play" %% "play-json" % playJsonVersion).cross(CrossVersion.for3Use2_13)

    val guice = ("com.google.inject" % "guice" % guiceVersion)
    val scalaGuice = ("net.codingwell" %% "scala-guice" % scalaGuiceVersion).cross(CrossVersion.for3Use2_13)

    val akkaHttp = ("com.typesafe.akka" %% "akka-http" % akkaHttpVersion).cross(CrossVersion.for3Use2_13)
    val akkaActor = ("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion).cross(CrossVersion.for3Use2_13)
    val akkaStream = ("com.typesafe.akka" %% "akka-stream" % akkaVersion).cross(CrossVersion.for3Use2_13)

    val slf4j = "org.slf4j" % "slf4j-nop" % "1.7.10"
    val h2 = "com.h2database" % "h2" % "1.4.187"

    // "org.slf4j" % "slf4j-nop" % "2.0.0-alpha7"

    val slick1 = ("com.typesafe.slick" %% "slick" % "3.3.3").cross(CrossVersion.for3Use2_13)
    val slick2 = ("com.typesafe.slick" %% "slick-hikaricp" % "3.3.3").cross(CrossVersion.for3Use2_13)
    val mysql = "mysql" % "mysql-connector-java" % "8.0.29"
  }


val commonDependencies = Seq(

  dependencies.scalactic,
  dependencies.scalatest,

  dependencies.scalaXML,

  dependencies.akkaHttp,
  dependencies.akkaActor,
  dependencies.akkaStream,
  // dependencies.akkaXML,

  dependencies.playJson,
  dependencies.guice,
  dependencies.scalaGuice,

  dependencies.h2,
  dependencies.slf4j,
  dependencies.slick1,
  dependencies.slick2,
  dependencies.mysql

)


//clears terminal output from sl4j logging error messages coming from sl4j not from our code
// libraryDependencies += "org.slf4j" % "slf4j-nop" % "2.0.0-alpha7"