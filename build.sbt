name := "nooz"
version := "0.1.0"

scalaVersion := "2.12.1"

import play.sbt.PlayImport.PlayKeys._

libraryDependencies ++= Seq(
  "org.neo4j.driver" % "neo4j-java-driver" % "1.1.1",
  "com.typesafe.play" %% "play-json" % "2.6.0-M3"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)
