#!/usr/bin/env bash

FWDIR="$(cd "`dirname "$0"`"/..; pwd)"

rm -rf "$FWDIR"/myResult

"$FWDIR"/bin/spark-submit --class "DsaPerfAnalysticApp" --master local[2] "$FWDIR"/myApps/dsa_performance_analystic_2.10-1.0.jar

