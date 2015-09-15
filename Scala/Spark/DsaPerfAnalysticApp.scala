import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import java.text.SimpleDateFormat


object DsaPerfAnalysticApp {

  def main(args: Array[String]) {
  	
  	var startFrom = "";
    if(args.size > 0){
      startFrom = args(0) 
    }

	val format = new SimpleDateFormat("h:mm:ss.SSS")

  val sparkConf = new SparkConf().setAppName("DsaPerfAnalysticApp")
  // Get a SparkContext
  val sc = new SparkContext(sparkConf)

  // var req = sc.textFile("myData/catalina.out").filter(line => line.contains("PERFORMANCE TEST")).map(_.split(",")).map(r => (r(1), r(2)))
  var req = sc.textFile("myData/catalina.out").filter(line => line.contains("PERFORMANCE TEST")).map(_.split(",")).map(r => (r(1), r(2)))
  // var req = sc.textFile("hdfs://10.106.8.158:9000/user/scc-dev/performance/input/perf_1000.log").filter(line => line.contains("PERFORMANCE TEST")).map(_.split(",")).map(r => (r(1), r(2)))
  // var req = sc.textFile("hdfs://10.106.8.158:9000/user/scc-dev/performance/input/catalina.out").filter(line => line.contains("PERFORMANCE TEST")).map(_.split(",")).map(r => (r(1), r(2)))
  
	if(!startFrom.isEmpty()){req = req.filter(r => r._2.toLong > startFrom.toLong)}

	val sorted = req.reduceByKey((x, y) => (y.toLong - x.toLong).toString).map(r => (r._1.split('|')(2), r._2)).sortByKey(true)
	val resultArr = sorted.map(r => format.format(r._1.toLong) + "," + r._2)

	resultArr.coalesce(1,true).saveAsTextFile("myResult")
  // resultArr.coalesce(1,true).saveAsTextFile("hdfs://10.106.8.158:9000/user/scc-dev/performance/output")
  }
}

