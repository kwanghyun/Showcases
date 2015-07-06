#!/usr/bin/env bash

FWDIR="$(cd "`dirname "$0"`"/..; pwd)"

rm -rf "$FWDIR"/myResult

"$FWDIR"/bin/spark-submit --class "DsaPerfAnalysticApp" --master local[*] "$FWDIR"/myApps/dsa_performance_analystic_2.10-1.0.jar
# "$FWDIR"/bin/spark-submit --class "DsaPerfAnalysticApp" --master spark://10.106.8.158:7077 "$FWDIR"/myApps/dsa_performance_analystic_2.10-1.0.jar


