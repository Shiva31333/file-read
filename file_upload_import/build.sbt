
name := """file"""
organization := "com.file"
version := "1.0"
scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaVersion = "2.5.11"
  val scalaTestV = "2.12.4"
  val akkaHttpVersion = "10.0.11"
  Seq(
    "mysql" % "mysql-connector-java" % "5.1.22",
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "ch.megard" %% "akka-http-cors" % "0.2.1",
    "com.typesafe.play" %% "anorm" % "2.5.0",
    "com.typesafe.play" %% "play-slick" % "0.8.1",
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
    "org.scalatest" %% "scalatest" % "3.0.1" % Test,
    "jp.co.bizreach" %% "aws-s3-scala" % "0.0.11",
    "ch.lightshed" %% "courier" % "0.1.4",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.0.pr1",
    "com.typesafe.akka" %% "akka-slf4j" % "2.5.6",
    "org.typelevel" %% "cats-core" % "1.4.0",
    "commons-io" % "commons-io" % "2.3",
    "com.lambdaworks" % "scrypt" % "1.4.0",
    "org.json4s" %% "json4s-native" % "3.5.1",
    "org.json4s" %% "json4s-jackson" % "3.5.1"
  )

}

resolvers += "lightshed-maven" at "http://dl.bintray.com/content/lightshed/maven"

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe" at "https://repo.typesafe.com/typesafe/releases/"
Revolver.settings

javaOptions in run ++= Seq(
  "-XX:+UseNUMA",
  "-XX:-UseBiasedLocking",
  "-Xms256M",
  "-Xmx2G",
  "-Xss1G",
  "-XX:MaxPermSize=1024M",
  "-XX:+UseParallelGC",
  "-XX:MaxGCPauseMillis=100",
  "-XX:GCTimeRatio=19")

fork in run := true