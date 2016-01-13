import AssemblyKeys._ 


name := "kafka-streaming"
scalaVersion := "2.10.5"
version := "1.0"

resolvers += "Apache Staging" at "https://repository.apache.org/content/groups/staging/"


libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka_2.10" % "0.8.0",
  "org.apache.spark" %% "spark-core" % "1.2.1" % "provided",
  "org.apache.spark" %% "spark-sql"  % "1.2.1" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.2.1" % "provided",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.2.1",
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.2.0-rc1",
  "org.json4s" %% "json4s-native" % "3.2.10"
)

assemblySettings

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}