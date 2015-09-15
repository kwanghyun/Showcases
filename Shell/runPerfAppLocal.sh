#!/usr/bin/env bash
FWDIR="$(cd "`dirname "$0"`"/..; pwd)"

rm "$FWDIR"/myData/catalina.out
scp scc-dev@10.106.8.93:/opt/cisco/apache/tomcat/logs/catalina.out "$FWDIR"/myData

rm -rf "$FWDIR"/myResult

"$FWDIR"/bin/spark-submit --class "DsaPerfAnalysticApp" --master local[*] "$FWDIR"/myApps/dsa_performance_analystic_2.10-1.0.jar "$1"
