name          := "CatFu"
organization  := "de.htwg.se"
version       := "0.0.1"
scalaVersion  in ThisBuild := "2.11.11"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo


libraryDependencies ++= {
  val scalaTestV       = "3.0.1"
  val scalaMockV       = "3.2.2"
  Seq(
    "org.scalatest" %% "scalatest"                   % scalaTestV       % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV % "test"
  )
}

val playVersion = "2.6.7"
libraryDependencies += "junit" % "junit" % "4.8" % "test"
libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11+"
libraryDependencies += "com.typesafe.play" %% "play-json" % playVersion

fork in run := false
connectInput in run := true
