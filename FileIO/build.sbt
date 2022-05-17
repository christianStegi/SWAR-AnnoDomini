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
    val scalaXmlVersion_old = "2.1.0"
    val scalaXmlVersion_new = "2.11.0-M4"
    // val scalaXmlVersion = scalaXmlVersion_old
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

    // val scalaXML = ("org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion).cross(CrossVersion.for3Use2_13)
    val scalaXML = "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion
    
    val playJson = ("com.typesafe.play" %% "play-json" % playJsonVersion).cross(CrossVersion.for3Use2_13)

    val guice = ("com.google.inject" % "guice" % guiceVersion)
    val scalaGuice = ("net.codingwell" %% "scala-guice" % scalaGuiceVersion).cross(CrossVersion.for3Use2_13)


    // val akkaHttp = ("com.typesafe.akka" %% "akka-http" % akkaHttpVersion)
    // val akkaActor = ("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion)
    // val akkaStream = ("com.typesafe.akka" %% "akka-stream" % akkaVersion)
    val akkaHttp = ("com.typesafe.akka" %% "akka-http" % akkaHttpVersion).cross(CrossVersion.for3Use2_13)
    val akkaActor = ("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion).cross(CrossVersion.for3Use2_13)
    val akkaStream = ("com.typesafe.akka" %% "akka-stream" % akkaVersion).cross(CrossVersion.for3Use2_13)
    // val akkaXML = ("com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion).cross(CrossVersion.for3Use2_13)
    // val akkaXML = "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion
    //val akkaXML = ("com.typesafe.akka" %% "akka-http-xml_2.13" % akkaHttpVersion).cross(CrossVersion.for3Use2_13)


    //clears terminal output from sl4j logging error messages coming from sl4j not from our code
    // val loggerStuff = "org.slf4j" %% "slf4j-nop" % loggerStuffVersion

  }

   //libraryDependencies += ("com.typesafe.akka" % "akka-http-xml" % "3.0.0-RC1").cross(CrossVersion.for3Use2_13)
   //libraryDependencies += ("com.typesafe.akka" % "akka-http-xml" % "3.0.0-RC1")
  //libraryDependencies += "com.typesafe.akka" % "akka-http-xml" % "10.2.9"
  // libraryDependencies += "com.typesafe.akka" % "akka-http-xml_2.13" % "10.2.9"

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
  // dependencies.loggerStuff,
)

libraryDependencies += "org.slf4j" % "slf4j-nop" % "2.0.0-alpha7"
