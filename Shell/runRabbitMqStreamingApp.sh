#!/usr/bin/env bash
FWDIR="$(cd "`dirname "$0"`"/..; pwd)"

"$FWDIR"/bin/spark-submit --class "com.cisco.analytic.amqp.RabbitMQStreaming" --total-executor-cores 2 --deploy-mode client "$FWDIR"/myapps/CPCanalytic-assembly-1.0.jar 10.106.8.80 topic raw-event-exchange raw-event-comsumer-q admin admin demo_ks demo_tb

"$FWDIR"/bin/spark-submit --class "com.cisco.analytic.amqp.RabbitMQStreaming" --master spark://spark2:6066 --total-executor-cores 2 --deploy-mode cluster --conf "spark.executor.extraJavaOptions=-Dlog4j.configuration=file:/home/scc-dev/softwares/spark-1.6.0-bin-hadoop2.6/conf/log4j.properties" file://home/scc-dev/softwares/spark-1.6.0-bin-hadoop2.6/myapps/CPCanalytic-assembly-1.0.jar 10.106.8.80 topic raw-event-exchange raw-event-comsumer-q admin admin demo_ks demo_tb

bin/spark-submit --class org.apache.spark.examples.SparkPi --deploy-mode cluster --master spark://spark2:7077 $SPARK_HOME/examples/lib/spark-examples_version.jar 10
