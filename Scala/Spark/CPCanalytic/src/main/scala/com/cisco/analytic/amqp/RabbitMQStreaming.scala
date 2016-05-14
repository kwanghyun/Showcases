package com.cisco.analytic.amqp

import com.stratio.receiver.RabbitMQUtils
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{DataFrame, SaveMode, SQLContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * Created by kwjang on 5/9/16.
  */
object RabbitMQStreaming {

  def main(args: Array[String]) {

    val conf = new SparkConf()
      .set("spark.cassandra.connection.host", "spark2")
      .setAppName("rabbitmq-streaming-handler")
//      .setIfMissing("spark.master", "local[*]")
      .setIfMissing("spark.master", "spark://spark2:7077")
//    val sc = new SparkContext(conf);
    val ssc = new StreamingContext(conf, Seconds(10))

    val sqlContext = new SQLContext(ssc.sparkContext)

    val receiverStream = RabbitMQUtils.createStream(ssc, Map(
      "host" -> "10.106.8.80",
      "exchangeName" -> "spring-boot-exchange",
      "exchangeType" -> "topic",
      "username" -> "admin",
      "password" -> "admin"
    ))

    // Start up the receiver.
    receiverStream.start()
    println("[@@ RabbitMQStreaming] Receiver started...")

    // Fires each time the configured window has passed.
    receiverStream.foreachRDD(rawData => {
      if (rawData.count() > 0) {

        println("[@@ RabbitMQStreaming] Got a new messages !!!! => " + rawData.count())
        val ctxJson = sqlContext.read.json(rawData)
        println(s"------ ctxJson.columns" , ctxJson.columns.toList)
        ctxJson.write.format("org.apache.spark.sql.cassandra")
          .options(Map("table" -> "demo_tb", "keyspace" -> "demo_ks"))
          .mode(SaveMode.Append).save()
        println("[@@ RabbitMQStreaming] DONE for this window")
      }
      else {
        println("[@@ RabbitMQStreaming] No new messages...")
      }
    })

    ssc.start() // Start the computation
    ssc.awaitTermination() // Wait for the computation to terminate
  }

//  def writeCassandra(sc:SparkContext, df: DataFrame, table: String, keyspace: String) =
//  {
//    val dfSchema = df.columns.toList
//    val cassTable = sc.cassandraTable(keyspace, table)
//    val cassSchema = cassTable.selectedColumnNames.toSet
//    val newCols = dfSchema.filterNot(cassSchema)
//
//    if(!newCols.isEmpty) {
//      val cassTable = sc.cassandraTable(keyspace, table)
//      cassTable.connector.withSessionDo {
//        session = {
//          for (col - newCols)
//            session.execute(s"ALTER TABLE $keyspace.$table ADD $col double")
//        } } }
//
//    df.write .format("org.apache.spark.sql.cassandra")
//      .options(Map( "table" - table, "keyspace" - keyspace))
//      .mode(SaveMode.Append).save()
//  }
}