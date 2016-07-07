import sbt.Defaults.itSettings

name := "airports"

val domain = project settings itSettings setSbtFiles file("build.sbt")

val cli = project settings itSettings setSbtFiles file("build.sbt") dependsOn domain settings(mainClass := Some("com.github.e8kor.app.Application"))

val root = project in file(".") aggregate(cli, domain) settings(
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.8"
  )