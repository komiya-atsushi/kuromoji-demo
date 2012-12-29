import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "kuromoji-demo"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.atilika.kuromoji" % "kuromoji" % "0.7.7",
    "org.webjars" % "webjars-play" % "2.0",
    "org.webjars" % "bootstrap" % "2.2.2"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    resolvers += "Atilika Open Source repository" at "http://www.atilika.org/nexus/content/repositories/atilika"
  )
}
