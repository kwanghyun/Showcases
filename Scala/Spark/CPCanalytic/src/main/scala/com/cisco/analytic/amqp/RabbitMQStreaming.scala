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
      //      .set("spark.cassandra.connection.host", "spark2")
      .setAppName("rabbitmq-streaming-handler")
      .setIfMissing("spark.master", "spark://spark2:7077")

    if (args.length < 7) {

      println(s"Wrong parameters $args.length")
      println(s"Usage args(0) $args(0) args(1) $args(1) ");
      println("<rabbitmq.host> <rabbitmq.exchangetype> <rabbitmq.exchangename> <rabbitmq.username> <rabbitmq.password> <cassandra.keyspace> <cassandra.table>");

    } else {

      val rbmq_host = args(0)
      val rbmq_exchangetype = args(1)
      val rbmq_exchangename = args(2)
      val rbmq_queuename = args(3)
      val rbmq_username = args(4)
      val rbmq_password = args(5)
      val cass_ks = args(6)
      val cass_tb = args(7)

      println(s"@@@@@@@@@@@@@@@@@@ rbmq_host -> $rbmq_host");
      println(s"@@@@@@@@@@@@@@@@@@ rbmq_exchangetype -> $rbmq_exchangetype");
      println(s"@@@@@@@@@@@@@@@@@@ rbmq_exchangename -> $rbmq_exchangename");
      println(s"@@@@@@@@@@@@@@@@@@ rbmq_queuename -> $rbmq_queuename");
      println(s"@@@@@@@@@@@@@@@@@@ rbmq_username -> $rbmq_username");
      println(s"@@@@@@@@@@@@@@@@@@ rbmq_password -> $rbmq_password");
      println(s"@@@@@@@@@@@@@@@@@@ cass_ks -> $cass_ks");
      println(s"@@@@@@@@@@@@@@@@@@ cass_tb -> $cass_tb");

      val ssc = new StreamingContext(conf, Seconds(10))
      val sqlContext = new SQLContext(ssc.sparkContext)

      //    val receiverStream = RabbitMQUtils.createStream(ssc, Map(
      //      "host" -> "10.106.8.80",
      //      "exchangeName" -> "spring-boot-exchange",
      //      "exchangeType" -> "topic",
      //      "username" -> "admin",
      //      "password" -> "admin"
      //    ))

      val receiverStream = RabbitMQUtils.createStream(ssc, Map(
        "host" -> rbmq_host,
        "exchangeName" -> rbmq_exchangename,
        "exchangeType" -> rbmq_exchangetype,
        "queueName" -> rbmq_queuename,
        "username" -> rbmq_username,
        "password" -> rbmq_password
      ))

      // Start up the receiver.
      receiverStream.start()
      println("[@@ RabbitMQStreaming] Receiver started...")

      // Fires each time the configured window has passed.
      receiverStream.foreachRDD(rawData => {
        if (rawData.count() > 0) {
//          try {
            println("[@@ RabbitMQStreaming] Got a new messages !!!! => " + rawData.count())
            val ctxJson = sqlContext.read.json(rawData)
            println(s"------ ctxJson.columns", ctxJson.columns.toList)
            ctxJson.write.format("org.apache.spark.sql.cassandra")
              .options(Map("table" -> cass_tb, "keyspace" -> cass_ks))
              .mode(SaveMode.Append).save()
            println("[@@ RabbitMQStreaming] DONE for this window")
//          } catch {
//            case e: Exception => println("[@@ RabbitMQStreaming] exception caught: " + e.getStackTrace);
//          }
        }
        else {
          println("[@@ RabbitMQStreaming] No new messages...")
        }
      })



      ssc.start() // Start the computation
      ssc.awaitTermination() // Wait for the computation to terminate

    }
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