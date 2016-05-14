//package com.cisco.analytic.amqp

//import org.apache.spark._
//import org.apache.spark.streaming._
//import org.apache.spark.storage.StorageLevel
//import org.apache.spark.streaming.mqtt._

// Create a local StreamingContext with two working thread and batch interval of 1 second.
// The master requires 2 cores to prevent from a starvation scenario.

/**
  * Created by kwjang on 5/10/16.
  */
//object MqttStreaming {
//  def main(args: Array[String]) {
//    val conf = new SparkConf ().setMaster ("local[2]").setAppName ("MqttStreaming-Demo")
//    val ssc = new StreamingContext (conf, Seconds (10) )
//    val brokerUrl = "tcp://192.168.2.26:1883"
//    val topic = "amp.topic"
//
//    val events = MQTTUtils.createStream (ssc, brokerUrl, topic, StorageLevel.MEMORY_ONLY_SER_2)
//
//    events.print ()
//    ssc.start ()
//    ssc.awaitTermination ()
//  }
//}
