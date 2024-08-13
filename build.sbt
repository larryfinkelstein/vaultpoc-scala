ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "vaultpoc-scala"
  )

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.4.3",
  "com.github.pathikrit" %% "better-files" % "3.9.2",
  "com.softwaremill.sttp.client3" %% "core" % "3.9.7",
  "com.softwaremill.sttp.client3" %% "circe" % "3.9.7",
  "io.circe" %% "circe-generic" % "0.14.1"
)
