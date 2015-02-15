name := "spray-actor-per-request"

organization := "nl.spockz"

scalaVersion := "2.11.4"

scalacOptions := Seq("-deprecation", "-encoding", "utf8")

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "com.typesafe.akka"                       %%  "akka-actor"                  % "2.3.6"

libraryDependencies += "io.spray"      %% "spray-can"   % "1.3.1"

libraryDependencies += "io.spray"      %% "spray-routing"   % "1.3.1"

libraryDependencies += "io.spray"      %% "spray-testkit"   % "1.3.1" % "test"

libraryDependencies +=  "org.json4s"   %% "json4s-native" % "3.2.11"

//libraryDependencies += "org.scalatest" %% "scalatest"       % "2.2.1" % "test"

libraryDependencies += "org.specs2"    %% "specs2"          % "2.3.13" % "test"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.3" % "test"

libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "2.1.3" % "test"


enablePlugins(GatlingPlugin)

Revolver.settings
