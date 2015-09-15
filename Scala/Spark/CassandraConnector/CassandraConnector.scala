import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils

object CassandraConnDemo extends App {
  import com.datastax.spark.connector._
  import TestEvents._

  // def main(args: Array[String]) {    
    val conf = new SparkConf()
      .set("spark.cassandra.connection.host", "10.106.9.157")
      .set("spark.driver.allowMultipleContexts", "true")
      .setMaster("local[*]")
      .setAppName("app2")

    val ssc = new StreamingContext(conf, Seconds(2))
    val sc = new SparkContext(conf)
    var sqlContext = new SQLContext(sc)
    val json = sc.parallelize(Seq("""{"user":"helena","commits":98, "month":12, "year":2014}""",
        """{"user":"pkolaczk", "commits":42, "month":12, "year":2014}"""))

    CassandraConnector(conf).withSessionDo { session =>
      session.execute("DROP KEYSPACE IF EXISTS githubstats")
      session.execute("CREATE KEYSPACE githubstats WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
      session.execute(
        """CREATE TABLE githubstats.monthly_commits (
          |user VARCHAR PRIMARY KEY,
          |commits INT,
          |month INT,
          |year INT)""".stripMargin)
    }

    sqlContext.jsonRDD(json).map(SLAuditEvents(_)).saveToCassandra("githubstats","monthly_commits")
    sc.cassandraTable[SLAuditEvents]("githubstats","monthly_commits").collect foreach println

    sc.stop()
}

object TestEvents {

  sealed trait Event extends Serializable
  trait AuditEvent extends Event
  trait LogEvent extends Event
  
  case class SLAuditEvents(user: String, commits: Int, month: Int, year: Int) extends AuditEvent
  object SLAuditEvents {
    def apply(r:Row): SLAuditEvents = SLAuditEvents(
      r.getString(2), r.getInt(0), r.getInt(1), r.getInt(3))
  }
}

