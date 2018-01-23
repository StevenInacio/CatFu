name          := "CatFu"
organization  := "de.htwg.se"
version       := "0.0.1"
scalaVersion  in ThisBuild := "2.11.11"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo


libraryDependencies ++= {
  val scalaTestV       = "3.0.1"
  val scalaMockV       = "3.2.2"
  val playVersion      = "2.6.7"
  Seq(
    "org.scalatest" %% "scalatest"                   % scalaTestV       % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV       % "test",
    "com.typesafe.play" %% "play-json" % playVersion
  )
}

libraryDependencies += "junit" % "junit" % "4.8" % "test"


fork in run := false
connectInput in run := true
