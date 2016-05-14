
import com.datastax.spark.connector._
import org.apache.spark.sql.{SaveMode, SQLContext, Row}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest._
import scala.collection.mutable.ArrayBuffer
import scala.util.parsing.json.JSON

/**
  * Created by kwjang on 5/10/16.
  */
case class JSONException extends Exception

/*
class JsonParser() extends Serializable{
  private var collections = new ArrayBuffer[Any]()
  private var keys:Seq[String] = Seq.empty

  def parse(data:RDD[String]) = {

    println(s"@@input :: ",data)
    data.foreach(x => {
      val parsed = JSON.parseFull(x)

      println(s"@@Parsed :: ",parsed)
      parsed match {
        case Some(map:Map[String, Any]) => {
          if(keys.isEmpty){
            keys = map.keySet.toList
            println(s"@@keys => " ,keys)
          }
          collections += map.values.toList
          println(s"@@collection => " ,collections)
        }
        case _ => JSONException
      }
      (collections, keys)
    })
    println(s"###### collections" , collections)
    println(s"###### keys" , keys)
    (collections, keys)
  }

  def parseRawJson = (x:String) => {
    val parsed = JSON.parseFull(x)

    println(s"@@Parsed :: ",parsed)
    parsed match {
      case Some(m:Map[String, Any]) => {
        collections += m.values.toSeq
        if(keys.isEmpty){
          keys = m.keySet.toSeq
          println(s"@@keys => " ,keys)
        }
        println(s"@@collection => " ,collections)
      }
      case _ => JSONException
    }
    println(s"###### collections" , collections)
    println(s"###### keys" , keys)
    (collections, keys)
  }
}
*/

class RabbitMqStreamingSpec extends FlatSpec
  with BeforeAndAfter
  with Matchers {


  sealed trait StreamModel extends Serializable
  case class Address(val name:String, val address:String,val city:String,val state:String) extends StreamModel
  case class Event(key:String, value:String) extends StreamModel
  object Event {
    def apply(r: Row): Event = Event(
      r.getString(0), r.getString(1))
  }

  private val master = "local[1]"
  private val appName = "example-spark"

  private var sc: SparkContext = _



  before {
    val conf = new SparkConf()
      .setMaster(master)
      .setAppName(appName)
      .set("spark.cassandra.connection.host", "10.106.9.91")

    sc = new SparkContext(conf)
  }

  after {
    if (sc != null) {
      sc.stop()
    }
  }



  "A RabbitMqStreaming Test" should "be " in {

    val rawJson = sc.parallelize(Seq(
      """{"event_name":"StaffDepartureEvent", "timestamp":1463168476791, "payload":"payload"}""",
      """{"event_name":"UserDepartureEvent", "timestamp":1463168476792, "payload":"payload"}"""))

//    val rawJson = sc.parallelize(Seq(
//      """{"event_name":"UserDepartureEvent", "timestamp":1463168476797, "payload":"payload"}"""))
//val rawJson = sc.parallelize(Seq(
//  """{"event_name":"StaffDepartureEvent", "timestamp":1463168476792, "payload":"payload"}"""))

//    val rawJson = sc.parallelize(Seq(
//      """{"event_name":"StaffDepartureEvent", "timestamp":1463168476797, "payload":"payload"}"""))


    val sqlContext = new SQLContext(sc)
    val ctxJson = sqlContext.read.json(rawJson)

//    ctxJson.write.format("org.apache.spark.sql.cassandra").options(Map("table" -> "demo_tb", "keyspace" -> "demo_ks")).mode(SaveMode.Append).save()

  }

  it should "save the data on cassandra" in {

    //    val rawJson = sc.parallelize(Seq(
//      """{"event_name":"StaffDepartureEvent", "timestamp":"2016-04-19T19:30:00Z", "payload":"payload" "name":"Yang","city":"San Jose","state":"CA"}""",
//      """{"event_name":"UserDepartureEvent", "timestamp":"2016-04-19T19:40:00Z", "payload":"payload","name":"Yin","city":"Columbus","state":"Ohio"}"""))
//    rawJson.foreach(println)
//    rawJson.saveToCassandra("demo_ks","demo_tb")
//
//    rawJson.count should be (2)
  }
}
