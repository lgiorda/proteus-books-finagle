name := "Proteus Books Finagle"
 
scalaVersion := "2.8.1"
 
seq(webSettings: _*)

// If using JRebel with 0.1.0 of the sbt web plugin
//jettyScanDirs := Nil
// using 0.2.4+ of the sbt web plugin
scanDirectories in Compile := Nil

// you can also add multiple repositories at the same time
resolvers ++= Seq(
  "Scala Tools Snapshot" at "http://scala-tools.org/repo-releases/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
  "twitter-repo" at "http://maven.twttr.com/"
)

libraryDependencies += "org.mortbay.jetty" % "jetty" % "6.1.22" % "container"

libraryDependencies ++= {
  val liftVersion = "2.4-M4" // Put the current/latest lift version here
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile",
    "net.liftweb" %% "lift-wizard" % liftVersion % "compile",
    "junit" % "junit" % "4.7" % "test",
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "com.h2database" % "h2" % "1.2.147"
    )
}

// Finagle Version of Proteus Project
libraryDependencies ++= Seq(
  "edu.ciir" % "proteus-finagle" % "0.1"
)
