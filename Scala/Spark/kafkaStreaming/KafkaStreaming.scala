import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.storage.StorageLevel

import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}


object KafkaStreamingDemo extends App {
  import com.datastax.spark.connector._
  import EventMap._

  // def main(args: Array[String]) {    
    val conf = new SparkConf()
      .set("spark.cassandra.connection.host", "10.106.9.157")
      .set("spark.driver.allowMultipleContexts", "true")
      .setMaster("local[*]")
      .setAppName("app2")

    val ssc = new StreamingContext(conf, Seconds(2))
    val sc = new SparkContext(conf)
    var sqlContext = new SQLContext(sc)

    System.out.println("################################### Configuration Done")

    val strProducer = Producer[String]("sl_alert_demo_topic")
    
    System.out.println("################################### Create Producer Done")

    // Set up the input DStream to read from Kafka (in parallel)
    // val kafkaStream = KafkaUtils.createStream(ssc, "10.106.9.157:2181","audit-group", Map("sl_audit_topic" -> 1), StorageLevel.MEMORY_ONLY).map(_._2)

    System.out.println("################################### Create Kafka Stream")

    kafkaStream.foreachRDD { rdd =>
       /* this check is here to handle the empty collection error
          after the 3 items in the static sample data set are processed */
        // JsonRDD return disordered sequence, so use SparkSQL make sure the order.
        System.out.println("################################### 1")
        if (rdd.toLocalIterator.nonEmpty) {
          System.out.println("################################### 2")
          sqlContext.jsonRDD(rdd).registerTempTable("audit_table")
          val data = sqlContext.sql(
            """SELECT site, bank, userId, action, time, status, trackingNumber, releaseCode FROM audit_table """)
            .map(SLAuditEvents(_))/*.foreach(println)*/
    //         // .saveToCassandra("githubstats","monthly_commits")
          System.out.println("################################### " + data.toString);
          // strProducer.send(data.toString)
        }
    }

    kafkaStream.print
    
    ssc.start()
    ssc.awaitTermination()
    System.out.println("################################### " + "Closing Producer");

}

object EventMap {

  sealed trait Event extends Serializable
  trait AuditEvent extends Event
  trait LogEvent extends Event
  
  case class SLAuditEvents(site: String, bank: String, userId: String, action: String, time: Long, status: String, trackingNumber: String, releaseCode: String) extends AuditEvent
  object SLAuditEvents {
    def apply(r:Row): SLAuditEvents = SLAuditEvents(
      r.getString(0), r.getString(1), r.getString(2), r.getString(3), r.getLong(4), r.getString(5),r.getString(6),r.getString(7))
  }
}

        
