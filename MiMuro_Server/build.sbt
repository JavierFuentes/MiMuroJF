//import play.Project._

name := "MiMuro_Server"

//version := "1.0"

//playJavaSettings

resolvers += DefaultMavenRepository

resolvers += JavaNet1Repository

resolvers += "Local Maven Repository" at "file:///"+Path.userHome+"/.m2/repository"

resolvers += "Java.Net" at "http://download.java.net/maven/2/"

val version = "1.0-SNAPSHOT"

val spring_version = "3.2.3.RELEASE"

val selenium_version = "2.32.0"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.6.1",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",  // Integración con Heroku
  "mysql" % "mysql-connector-java" % "5.1.31",
  "org.hibernate" % "hibernate-entitymanager" % "3.4.0.GA" exclude("org.slf4j", "slf4j-api") exclude("org.slf4j", "slf4j-log4j12"),
  "org.springframework" % "spring-test" % spring_version,
  "org.springframework" % "spring-context" % spring_version,
  "org.springframework" % "spring-orm" % spring_version,
  "org.springframework" % "spring-aop" % spring_version,
  "org.springframework" % "spring-expression" % spring_version,
  "org.springframework" % "spring-context-support" % spring_version,
  "org.springframework" % "spring-web" % spring_version,
  "junit" % "junit" % "4.8.2",
  "org.apache.commons" % "commons-lang3" % "3.0.1" exclude("maven-plugins", "maven-cobertura-plugin") exclude("maven-plugins", "maven-findbugs-plugin"),
  "commons-dbcp" % "commons-dbcp" % "1.2.2" exclude("maven-plugins", "maven-cobertura-plugin") exclude("maven-plugins", "maven-findbugs-plugin") exclude("javax.resource", "connector") exclude("javax.jms", "jms") exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri"),
  "com.h2database" % "h2" % "1.4.178",
  "javax" % "javaee-api" % "6.0-SNAPSHOT",
  "org.apache.commons" % "commons-io" % "1.3.2",
  "javax.json" % "javax.json-api" % "1.0",
  "javax" % "javaee-api" % "6.0",
  "org.glassfish" % "javax.json" % "1.0.2",
  "uk.ac.gate" % "gate-core" % "8.0"
)

play.Project.playJavaSettings
