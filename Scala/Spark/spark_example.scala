
/****************************************************
* One Way :: Responder -> Requester
****************************************************/
import java.text.SimpleDateFormat
//Responder
val res1 = sc.textFile("myData/filelog-info_10.106.8.158.log").map(_.split(",")).map(r => (r(2), r(3)))
val res2 = sc.textFile("myData/filelog-info_10.106.8.160.log").map(_.split(",")).map(r => (r(2), r(3)))
val resp = res1.union(res2)

//Requester
val req = sc.textFile("myData/perf_1000_2_22Jun2015.log").filter(line => line.contains("PERFORMANCE TEST")).map(_.split(",")).map(r => (r(1), r(2)))


//Join and I/O
val result = resp.join(req)
val resultMap = result.map(r => (r._1, (r._2._2.toLong) - r._2._1.replace("\"", "").toLong))

	
val unsorted = result.map(r => (r._1.split('|')(2), ((r._2._2.toLong) - r._2._1.replace("\"", "").toLong)))
val grouped = unsorted.groupBy(_._1).map(r => r._1, r._2.avg)

grouped.map(r => r._1, r._2.avg)


val sorted = unsorted.sortByKey(true)

val format = new SimpleDateFormat("h:mm:ss.SSS")
val resultArr = sorted.map(r => format.format(r._1.toLong) + "," + r._2)

resultArr.coalesce(1,true).saveAsTextFile("myResult")


/****************************************************
* Round trip :: Requester -> Responder -> Requester
****************************************************/







/****************************************************
* HELPER
****************************************************/
//val Unsorted = result.map(r => (r._1.split('|')(2), r._1 + "," + ((r._2._2.toLong) - r._2._1.replace("\"", "").toLong)))








val resultMap = result.map(r => (r._1, (r._2._2.toLong) - r._2._1.replace("\"", "").toLong))
