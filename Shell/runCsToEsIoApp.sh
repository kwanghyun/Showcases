#!/usr/bin/env bash
FWDIR="$(cd "`dirname "$0"`"/..; pwd)"

"$FWDIR"/bin/spark-submit --class "com.cisco.analytic.CassToElasticIO" --total-executor-cores 8 --deploy-mode client "$FWDIR"/myapps/cassToElasticIO-assembly-1.0.jar "2016-05-16 00:00:00" "2016-05-27 00:00:00" 10.106.8.80 9200

