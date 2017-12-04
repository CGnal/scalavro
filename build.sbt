
//////////////////////////////////////
// Root project settings
//////////////////////////////////////

name := Common.PROJECT_NAME

unidocSettings

Common.commonSettings

//////////////////////////////////////
// Subproject definitions
//////////////////////////////////////

lazy val core = Common.subproject("core").dependsOn(util)

lazy val util = Common.subproject("util")

//////////////////////////////////////
// Common settings shared by all projects
//////////////////////////////////////

version in ThisBuild := Common.PROJECT_VERSION

organization in ThisBuild := Common.ORGANIZATION

scalaVersion in ThisBuild := Common.SCALA_VERSION

libraryDependencies in ThisBuild ++= Seq(
      "org.scala-lang" % "scala-reflect" % Common.SCALA_VERSION,
      "org.slf4j" % "slf4j-api" % Common.SLF4J_VERSION,
      "com.typesafe.scala-logging" %% "scala-logging-slf4j" % Common.SCALA_SLF4J_LOGGING_VERSION,
      "com.typesafe.scala-logging" %% "scala-logging-api" % Common.SCALA_SLF4J_LOGGING_VERSION,
      "org.slf4j" % "slf4j-simple" % Common.SLF4J_VERSION % "test",
      "org.scalatest" %% "scalatest" % Common.SCALATEST_VERSION % "test"
    )

scalacOptions in (ThisBuild, Compile) ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature"
    )

parallelExecution in (ThisBuild, Test) := false

fork in (ThisBuild, Test) := true

//////////////////////////////////////
// Publishing settings
//////////////////////////////////////

publishMavenStyle in (ThisBuild) := true

pomIncludeRepository in (ThisBuild) := { _ => false }

licenses in (ThisBuild) := Seq(
      "BSD-style" -> url("http://opensource.org/licenses/BSD-2-Clause")
    )

homepage in (ThisBuild) := Some(url("http://genslerappspod.github.io/scalavro/"))

pomExtra in (ThisBuild) := (
      <scm>
        <url>git@github.com:GenslerAppsPod/scalavro.git</url>
        <connection>scm:git:git@github.com:GenslerAppsPod/scalavro.git</connection>
      </scm>
      <developers>
        <developer>
          <id>ConnorDoyle</id>
          <name>Connor Doyle</name>
          <url>http://gensler.com</url>
        </developer>
      </developers>
    )

val nexusRepoBase = "http://repo.eligotech.com/nexus"

def snapshotsRepo = "Eligotech Snapshots" at s"$nexusRepoBase/content/repositories/snapshots"

def releasesRepo  = "Eligotech Releases"  at s"$nexusRepoBase/content/repositories/releases"
def defaultOrganization = "com.cgnal"

def publishRepo(isSnapshot: Boolean) = if (isSnapshot) Some { snapshotsRepo } else Some { releasesRepo }


isSnapshot in (ThisBuild) := version.value.endsWith("SNAPSHOT")

publishTo in (ThisBuild) := publishRepo(isSnapshot.value)