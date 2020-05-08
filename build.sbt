name := """play-demo"""
organization := "per.cs"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).enablePlugins(SbtWeb)

scalaVersion := "2.11.11"
val circeVersion = "0.9.3"

libraryDependencies += filters
libraryDependencies ++= Seq(
  evolutions,
  jdbc,
  // Start with this one
  "org.tpolecat" %% "doobie-core" % "0.5.2",
  // H2 driver 1.4.197 + type mappings.
  "org.tpolecat" %% "doobie-h2" % "0.5.2",
  "org.tpolecat" %% "doobie-hikari" % "0.5.3" exclude("com.zaxxer", "HikariCP"),
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion
)

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "per.cs.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "per.cs.binders._"
