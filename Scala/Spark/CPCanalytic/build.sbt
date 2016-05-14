import AssemblyKeys._

assemblySettings

name := "CPCanalytic"
version := "1.0"
scalaVersion := "2.10.5"

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
//resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.0"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.0"
libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "1.6.0"
libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.2.6"


//libraryDependencies += "org.apache.spark" %% "spark-streaming_mqtt" % "1.6.0"
//libraryDependencies += "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.0.2"

libraryDependencies += "com.rabbitmq" % "amqp-client" % "3.6.1"
libraryDependencies += "com.stratio.receiver" %% "rabbitmq" % "0.3.0-SNAPSHOT"
libraryDependencies += "com.datastax.spark" % "spark-cassandra-connector_2.10" % "1.6.0-M2"
libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "3.0.0"
libraryDependencies += "com.google.guava" % "guava" % "18.0"
libraryDependencies += "com.twitter" % "jsr166e" % "1.1.0"
//libraryDependencies += "com.datastax.spark" % "spark-cassandra-connector_2.10" % "1.5.0"


// META-INF discarding
mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}
