
/****************************************************
* One Way :: Responder -> Requester
****************************************************/
import java.text.SimpleDateFormat

val format = new SimpleDateFormat("h:mm:ss.SSS")

val res1 = sc.textFile("myData/filelog-info_10.106.8.158.log").map(_.split(",")).map(r => (r(2), r(3)))
val res2 = sc.textFile("myData/filelog-info_10.106.8.160.log").map(_.split(",")).map(r => (r(2), r(3)))
val resp = res1.union(res2)

val req = sc.textFile("myData/perf_1000_2_22Jun2015.log").filter(line => line.contains("PERFORMANCE TEST")).map(_.split(",")).map(r => (r(1), r(2)))

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
val sorted = req.reduceByKey((x, y) => (y.toLong - x.toLong).toString).map(r => (r._1.split('|')(2), r._2)).sortByKey(true)
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


