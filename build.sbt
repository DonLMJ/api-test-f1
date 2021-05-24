name := "api-test-f1"

version := "0.1"

scalaVersion := "2.13.6"
val mockitoScalaVersion = "1.16.15"

libraryDependencies ++= Seq(
  // logging
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "net.logstash.logback" % "logstash-logback-encoder" % "6.2",
  "org.codehaus.janino" % "janino" % "3.0.15",


  "io.rest-assured" % "rest-assured" % "4.3.3" % Test,
  "io.rest-assured" % "scala-support" % "4.3.3" % Test,
  "io.rest-assured" % "json-schema-validator" % "4.3.3" % Test,
  "io.spray" %%  "spray-json" % "1.3.6" % Test,

  "com.atlassian.oai" % "swagger-request-validator" % "2.15.0" % Test,
  "com.atlassian.oai" % "swagger-request-validator-restassured" % "2.15.0" % Test,

  "org.mockito" %% "mockito-scala" % mockitoScalaVersion % Test,
  "org.mockito" %% "mockito-scala-scalatest" % mockitoScalaVersion % Test,

  "io.qameta.allure" %% "allure-scalatest" % "2.13.8",
  "org.scalatest" %% "scalatest" % "3.2.3" % Test
)


testOptions in Test ++= Seq(
  Tests.Argument(TestFrameworks.ScalaTest, "-oD"),
  Tests.Argument(TestFrameworks.ScalaTest, "-C", "io.qameta.allure.scalatest.AllureScalatest")
)


