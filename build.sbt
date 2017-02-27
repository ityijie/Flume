name := "flume"

version := "1.0"

scalaVersion := "2.10.4"




// https://mvnrepository.com/artifact/org.apache.flume.flume-ng-clients/flume-ng-log4jappender

/*libraryDependencies += "log4j" % "log4j" % "1.2.17"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.5"*/

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.21"

libraryDependencies += "org.apache.flume" % "flume-ng-sdk" % "1.6.0"

libraryDependencies += "org.apache.flume.flume-ng-clients" % "flume-ng-log4jappender" % "1.6.0"

// https://mvnrepository.com/artifact/org.codehaus.jackson/jackson-core-asl
//libraryDependencies += "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13"

//resolvers += "uk" at "http://uk.maven.org/maven2/"
resolvers += "repo2" at "http://repo2.maven.org/maven2/"
