
/****************************************************
* One Way :: Responder -> Requester
****************************************************/
import java.text.SimpleDateFormat

val format = new SimpleDateFormat("h:mm:ss.SSS")

val res1 = sc.textFile("myData/filelog-info_10.106.8.158.log").map(_.split(",")).map(r => (r(2), r(3)))
val res2 = sc.textFile("myData/filelog-info_10.106.8.160.log").map(_.split(",")).map(r => (r(2), r(3)))
val resp = res1.union(res2)
"STATUS = smartlocker_app_events - SmartLockerAppEventProcessor Start, Envelope:"
val req = sc.textFile("myData/perf_1000_2_22Jun2015.log").filter(line => line.contains("PERFORMANCE TEST")).map(_.split(",")).map(r => (r(1), r(2)))
val req = req.filter(r => r._2.toLong > 1434956198424L)

val result = resp.join(req)
	
val unsorted = result.map(r => (r._1.split('|')(2), ((r._2._2.toLong) - r._2._1.replace("\"", "").toLong)))
val grouped = unsorted.combineByKey(
  (v) => (v, 1),
  (acc: (Long, Int), v) => (acc._1 + v, acc._2 + 1),
  (acc1: (Long, Int), acc2: (Long, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
  ).map{ case (key, value) => (key, value._1 / value._2.toFloat) }

val sorted = grouped.sortByKey(true)
val resultArr = sorted.map(r => format.format(r._1.toLong) + "," + r._2)

resultArr.coalesce(1,true).saveAsTextFile("myResult")


/****************************************************
* Round trip :: Requester -> Responder -> Requester
****************************************************/
import java.text.SimpleDateFormat

val format = new SimpleDateFormat("h:mm:ss.SSS")

val req = sc.textFile("myData/perf_1000.log").filter(line => line.contains("PERFORMANCE TEST")).map(_.split(",")).map(r => (r(1), r(2)))
val req = req.filter(r => r._2.toLong > 1434956198424L)

val sorted = filtered.reduceByKey((x, y) => (y.toLong - x.toLong).toString).map(r => (r._1.split('|')(2), r._2)).sortByKey(true)
val resultArr = sorted.map(r => format.format(r._1.toLong) + "," + r._2)

resultArr.coalesce(1,true).saveAsTextFile("myResult")


/****************************************************
* HELPER
****************************************************/
// MAP(String, String)
.map(r => (r._1.split('|')(2), r._2))

// MAP(String, String)
.map(r => (r._1.split('|')(2), r._1 + "," + ((r._2._2.toLong) - r._2._1.replace("\"", "").toLong)))

// MAP(String, (String, String))
.map(r => (r._1, (r._2._2.toLong) - r._2._1.replace("\"", "").toLong))

//DEBUG
req.take(20).foreach(println)



var src = sc.textFile("hdfs://10.106.8.158:9000/user/scc-dev/performance/flume/perf-.1437103335596")
val filtered = src.filter(line => line.contains("level"))
var map = filtered.map(_.split(",")).map(r => (r(0), r(1), r(2)))
map.take(10).foreach(println)


/****************************************************
* SPARK SQL
****************************************************/

// sc is an existing SparkContext.
val sqlContext = new org.apache.spark.sql.SQLContext(sc)
// createSchemaRDD is used to implicitly convert an RDD to a SchemaRDD.
import sqlContext.createSchemaRDD

case class Log(id: String, updatedOn: Long)

// var src = sc.textFile("hdfs://10.106.8.158:9000/user/scc-dev/performance/flume/perf-.1437103335596")
var src = sc.textFile("myData/perf_1000.log")
val filtered = src.filter(line => line.contains("PERFORMANCE TEST"))
val logs = filtered.map(_.split(",")).map(p => Log(p(1).trim, p(2).trim.toLong))
logs.registerTempTable("logs")

val result = sqlContext.sql("SELECT * FROM logs WHERE updatedOn < 1434956198424")
result.map(t => "MSG :: " + t(0)).collect().foreach(println)

performance/flume/perf-.1437103335596

/****************************************************
* Learning SQL
****************************************************/
//Per-key average 
rdd.mapValues(x => (x, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))


/****************************************************
* Spark performance debug
****************************************************/
var req = sc.textFile("myData/perf_1000.log").filter(line => line.contains("PERFORMANCE TEST")).map(_.split(",")).map(r => (r(0),r(1),r(2)))
var req1 = sc.textFile("myData/catalina.out").filter(line => line.contains("REQUESTOR OPEN LOCKER INVOKE")).map(_.split(",")).map(r => (r(0),r(1),r(2)))
var req1 = sc.textFile("myData/catalina.out").filter(line => line.contains("ERROR"))

var reqA = sc.textFile("myData/audit-smartlocker.log").map(_.split(",")).map(r => (r(0),r(1),r(2)))
var reqA = sc.textFile("myData/audit-smartlocker.log").filter(line => line.contains("ERROR"))


req1 = req1.filter(r => r._3.toLong > 1439790509000L)
req2 = req2.filter(r => r._3.toLong > 1439790509000L)

val req = req1.union(req2)
val filtered = req.filter(r => r._1.split('|')(1) != "AUTODOORCLOSE")


val sorted = filtered.reduceByKey((x, y) => (y.toLong - x.toLong).toString).map(r => (r._1.split('|')(1),  r._2)).sortByKey(true)

req.coalesce(1,true).saveAsTextFile("myResult")



/****************************************************
* Spark with Cassandra
****************************************************/
bin/spark-shell --jars lib/spark-cassandra-connector-assembly-1.2.0-SNAPSHOT.jar --conf spark.cassandra.connection.host=localhost --conf spark.cassandra.connection.native.port=9042 --conf spark.cassandra.auth.username=admin --conf spark.cassandra.auth.password=admin
bin/spark-shell --jars lib/spark-cassandra-connector-assembly-1.2.0-SNAPSHOT.jar --conf spark.cassandra.connection.host=localhost --conf spark.cassandra.connection.native.port=9164

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql._

val rdd = sc.cassandraTable("event_ks", "com_cisco_bam_smartlocker_kpi")
rdd.count



val rdd = sc.cassandraTable("sl_audit", "audit_events")
rdd.count
val firstRow = rdd.first
firstRow.columnNames

val cc = new org.apache.spark.sql.cassandra.CassandraSQLContext(sc)
val p = cc.sql("select * from sl_audit.audit_events")
p.collect.foreach(println)



import org.apache.spark.sql.cassandra.CassandraSQLContext
val cc = new CassandraSQLContext(sc)
val rdd: SchemaRDD = cc.sql("SELECT * from keyspace.table WHERE ...")

sc.getConf.set("spark.cassandra.connection.host", "10.106.9.157")


/****************************************************
* Spark Kafka
****************************************************/
bin/spark-shell --jars lib/spark-streaming-kafka_2.10-1.2.0.jar, lib/kafka_2.10-0.8.1.jar, lib/metrics-core-2.2.0.jar, lib/zkclient-0.4.jar

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils

val ssc = new StreamingContext(sc, Seconds(2))

val kafkaStream = KafkaUtils.createStream(ssc, "10.106.9.157:2181","audit-group", Map("sl_audit_topic" -> 1)).map(_._2)

kafkaStream.print()
ssc.start()



val zkQuorum = "10.106.9.157:2181"
val group = "test-group"
val topics = "test"
val numThreads = 1
val topicMap = topics.split(",").map((_,numThreads.toInt)).toMap

val lineMap = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)

val lines = lineMap.map(_._2)

val words = lines.flatMap(_.split(" "))

ssc.start
ssc.awaitTermination

/opt/infoobjects/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test


/****************************************************
* Run APP
****************************************************/

//Producer
cd C:\Users\kwjang\Documents\workspace-sts-3.6.4.RELEASE\EventGenerator\target
java -cp uber-EventProducer-1.0-SNAPSHOT.jar DemoProducer 
java -cp uber-EventProducer-1.0-SNAPSHOT.jar DemoProducer <msgCcount> <Interval>
java -cp uber-EventProducer-1.0-SNAPSHOT.jar DemoProducer 10 1

// Spark App
bin/spark-submit --class "KafkaStreamingDemo" myApps/kafka-streaming-assembly-1.0.jar 



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


import org.apache.spark.sql.{Row, SQLContext}
import EventMap._

var sqlContext = new SQLContext(sc)

val json = sc.parallelize(Seq(
    """{"user":"helena","commits":98, "month":3, "year":2015}""",
    """{"user":"jacek-lewandowski", "commits":72, "month":3, "year":2015}""",
    """{"user":"pkolaczk", "commits":42, "month":3, "year":2015}"""))

val json = sc.parallelize(Seq(
    """{"site":"site-1","bank":"bank-1", "userId":"user-1", "action":"action-1", "time":1440393990118, "status":"status-1", "trackingNumber":"T-1231", "releaseCode":"R-1231"}""",
    """{"site":"site-2","bank":"bank-2", "userId":"user-2", "action":"action-2", "time":1440393990218, "status":"status-2", "trackingNumber":"T-1232", "releaseCode":"R-1232"}""",
    """{"site":"site-3","bank":"bank-3", "userId":"user-3", "action":"action-3", "time":1440393990318, "status":"status-3", "trackingNumber":"T-1233", "releaseCode":"R-1233"}""",
    """{"site":"site-4","bank":"bank-4", "userId":"user-4", "action":"action-4", "time":1440393990418, "status":"status-4", "trackingNumber":"T-1234", "releaseCode":"R-1234"}""",
    """{"site":"site-5","bank":"bank-5", "userId":"user-5", "action":"action-5", "time":1440393990518, "status":"status-5", "trackingNumber":"T-1235", "releaseCode":"R-1235"}"""))

sqlContext.jsonRDD(json).registerTempTable("audit_table")
val result = sqlContext.sql("""SELECT site, bank, userId, action, time, status, trackingNumber, releaseCode FROM audit_table WHERE userId = "user-1" AND bank = "bank-1"  """)
val result = sqlContext.sql("""SELECT site, bank, userId, action, time, status, trackingNumber, releaseCode FROM audit_table  """)

val myTest = result.map(SLAuditEvents(_))
result.map(SLAuditEvents(_)).foreach(println)

val result = sqlContext.sql("SELECT user, commits, month, year FROM audit_table WHERE commits >= 5 AND year = 2015")
result.foreach(println)




/****************************************************
* WSO2 ESB KPI log replay
****************************************************/

val res = sc.textFile("myData/wso2esbcarbonlogs/wso2carbon.log.2015-01-15").filter(line => line.contains("STATUS = smartlocker_app_events - SmartLockerAppEventProcessor Start, Envelope:"))

val cutHead = res.map(_.split("http://ws.apache.org/commons/ns/payload\">")).map(r => (r(1)))
val cutTail = cutHead.map(_.split("</axis2")).map(r => (r(0)))

cutTail.coalesce(1,true).saveAsTextFile("tempTest")

// val cutHead = res.map(_.split("http://schemas.xmlsoap.org/soap/envelope/\"")).map(r => (r(0)))
val res2 = sc.textFile("myData/filelog-info_10.106.8.160.log").map(_.split(",")).map(r => (r(2), r(3)))
val resp = res1.union(res2)

val req = sc.textFile("myData/perf_1000_2_22Jun2015.log").filter(line => line.contains("STATUS = smartlocker_app_events - SmartLockerAppEventProcessor Start, Envelope:")).map(_.split(",")).map(r => (r(1), r(2)))
val req = req.filter(r => r._2.toLong > 1434956198424L)


val res = sc.textFile("myData/wso2esbcarbonlogs/wso2carbon.log.2015-02-15").filter(line => line.contains("SmartLockerEventThriftPublisher::mediate() - smartLockerEvent:"))
val cutHead2 = res2.map(_.split(" smartLockerEvent:SmartLockerEvent \\[")).map(r => (r(1).replace("(KHTML, like Gecko)", "(KHTML,like Gecko)").replace("length, width, height details available", "length,width,height details available")))
val cutHead2 = res2.map(_.split(" smartLockerEvent:SmartLockerEvent \\[")).map(r => (r(1)))

val cutTail2 = cutHead2.map(_.split("\\}] \\{com.cisco.smartlocker.publisher.SmartLockerEventThriftPublisher\\}")).map(r => (r(0)))

val arr = cutTail2.foreach( row => for ( col <- row.map(_.split(", "))) yield col.map(_.split("=")).map(r => r(0)) )

//TEST
val arr = for ( row <- cutTail2 ) for ( col <- row.split(", ")) if(!col.contains("=")) println(col)

val arr = for ( row <- cutTail2 ) for ( col <- row match{
  case x if(x.contains())
})



val arr = for ( row <- cutTail2 ) for ( col <- row.split(", ")) println(col.split("=").map(r=> r(0)))

val arr = for ( row <- cutTail2 ) for ( col <- row.split(", ")) println(col.split("=").map(r=> r(0) + "\"" + r(1) + "\""))
val arr = for ( row <- cutTail2 ) yield row  


val str = "eventType=20, description=SUPPORT_PACKAGEDETAILS, timestamp=1421351561348, transactionId=null, correlationId=605481221100, sessionId=null, appId=smart-locker, payload={http_requestMethod=POST, JMS_DESTINATION=smartlocker.app.raw.events, empId=5035613, stationId=, door=11, JMS_TIMESTAMP=1421351561349, receiverEmailId=null, city=Richmond Hill, lockerBankState=ONLINE, formId=null, packagingType=null, zipCode=L4C 3E4, action=packageDetails, bank=1, longitude=-79.442158, JMS_DELIVERY_MODE=2, displayHoursOfOpertion=Mon-Fri 9am-9pm, Sat 9am-6pm, Sun 10am-5pm, connection=Keep-Alive, host=localhost:8080, releaseCode=1TXONW, JMS_PRIORITY=4, addressLine2=, addressLine1=10870 Yonge Street, statusMessage=SUCCESS, siteAlertType=null, customerSignature=null, pickupRouteNumber=null, siteId=YKZAL, trackingNumber=605481221100, JMS_EXPIRATION=0, deliveryRouteNumber=null, redeliveryReason=null, latitude=43.892036, retrievalDate=null, siteStatus=OK, JMS_MESSAGE_ID=ID:207-34-238-72.agilit.telus.com-55879-1418303876188-1:1:3:8936:1, displayPhoneNumber=905-508-5265, customerFullName=null, state=ON, locationId=YKZAL, depositDate=1421346888000, timestamp=1421351561348, courierId=719832, id=28, statusCode=200, receiverMobileNumber=null, packageLength=null, shipTimestamp=null, displayAddress=10870 Yonge Street, Richmond Hill, ON, L4C 3E4 (P014), packageHeight=null, packageWidth=null, bankOfflineEndDateTime=null, bankOfflineStartDateTime=null, expiryDate=1421778888000, user-agent=Synapse-PT-HttpComponents-NIO, http_requestUrl=http://localhost:8080/smart-locker/support/packageDetails, packageStatus=IN_LOCKER, logicalName=Rexall Store #1313, JMS_REDELIVERED=false"



val filterList = Array(("displayHoursOfOpertion=", "connection="), ("statusMessage=", "customerSignature="))

def hf_filterNreplaceAll(str:String, filterList:Array[(String, String)]): String{
  var tempStr = str;
  for(filter <- filterList) {
    tempStr = hf_filterNreplace(tempStr, filter._1, filter._2)
  }
  tempStr;
}

def hf_filterNreplace(str:String, header:String, footer:String): String{
  val oldStr = str.substring(str.indexOfSlice(header) + header.length, str.indexOfSlice(footer))
  val newStr = str.substring(str.indexOfSlice(header) + header.length, str.indexOfSlice(footer)).replaceAll(", ", "||")
  str.replace(oldStr, newStr)
}

val header = "displayHoursOfOpertion="
val footer = "connection=";


for ( elem <- filterList) {
  println(elem._2)
} yield str 


val arr = for {
  row <- cutTail2
  filter <- filterList
  oldStr = row.substring(row.indexOfSlice(filter._1) + header.length, row.indexOfSlice(filter._2))
} yield {  
  row
}

  val oldStr = row.substring(row.indexOfSlice(filter._1) + header.length, row.indexOfSlice(filter._2))
  val newStr = row.substring(row.indexOfSlice(filter._1) + header.length, row.indexOfSlice(filter._2)).replaceAll(", ", "||")
  val result = row.replace(oldStr, newStr)


arr.take(5).foreach( a => {a.foreach(e => println(e + " "))})


val newArray = for (e <- arr) yield e for (i <- e) yield "\"" + i + "\""


cutTail2.coalesce(1,true).saveAsTextFile("tempTest")


cutHead2.take(10).foreach(println)
cutTail2.take(10).foreach(println)
