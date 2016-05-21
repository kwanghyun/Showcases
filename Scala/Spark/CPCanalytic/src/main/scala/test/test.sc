import com.datastax.spark.connector.SomeColumns
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer
import scala.util.parsing.json.JSON


//private val master = "spark://spark2:7077"
//private val appName = "test"
//
//
//val conf = new SparkConf()
//  .set("spark.cassandra.connection.host", "10.106.9.157")
//  .set("spark.driver.allowMultipleContexts", "true")
//val sc = new SparkContext(conf)
//
//  val rawJson = Seq(
//    """{"name":"Yang","city":"San Jose","state":"CA"}""",
//    """{"name":"Yin","city":"Columbus","state":"Ohio"}""")



val data = Seq(("cat",30), ("fox",40))
data.foreach(println(_))
//  val rawJson = Seq( """{"name":"Yang","address":{"city":"San Jose","state":"CA"}}""")
var collections = new ArrayBuffer[Any]()
var keys:Seq[String] = Seq.empty
var col = new ArrayBuffer[Any]()
col += List(1,2,3)
col += List(3,4,5)
println(col)
//foreach(k => println(s"", k , m(k))
//    rawJson.foreach(x => {
//      val parsed = JSON.parseFull(x)
//      println(parsed)
//      parsed match {
//        case Some(m: Map[String, Any]) => m("name") match {
//          case s: String => s
//          case _ => "Noe"
//        }
//        case _ => "No match"
//      }
//    })

var args = new Array[String](3)
args(0) = "1"
//args += "2"
//args += "3"
println(args.size)
