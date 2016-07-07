addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)


version := "0.1-SNAPSHOT"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.github.tototoshi" %% "scala-csv" % "1.3.3",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "org.typelevel" %% "cats" % "0.6.0"
)