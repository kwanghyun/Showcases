#!/usr/bin/env bash

FWDIR="$(cd "`dirname "$0"`"/..; pwd)"
export SPARK_HOME="$FWDIR"
EXAMPLES_DIR="$FWDIR"/examples

. "$FWDIR"/bin/load-spark-env.sh

if [ -n "$1" ]; then
  EXAMPLE_CLASS="$1"
  shift
else
  echo "Usage: ./bin/run-example <example-class> [example-args]" 1>&2
  echo "  - set MASTER=XX to use a specific master" 1>&2
  echo "  - can use abbreviated example class name relative to com.apache.spark.examples" 1>&2
  echo "     (e.g. SparkPi, mllib.LinearRegression, streaming.KinesisWordCountASL)" 1>&2
  exit 1
fi

if [ -f "$FWDIR/RELEASE" ]; then
  JAR_PATH="${FWDIR}/lib"
else
  JAR_PATH="${EXAMPLES_DIR}/target/scala-${SPARK_SCALA_VERSION}"
fi

JAR_COUNT=0

for f in ${JAR_PATH}/spark-examples-*hadoop*.jar; do
  if [[ ! -e "$f" ]]; then
    echo "Failed to find Spark examples assembly in $FWDIR/lib or $FWDIR/examples/target" 1>&2
    echo "You need to build Spark before running this program" 1>&2
    exit 1
  fi
  SPARK_EXAMPLES_JAR="$f"
  JAR_COUNT=$((JAR_COUNT+1))
done

if [ "$JAR_COUNT" -gt "1" ]; then
  echo "Found multiple Spark examples assembly jars in ${JAR_PATH}" 1>&2
  ls ${JAR_PATH}/spark-examples-*hadoop*.jar 1>&2
  echo "Please remove all but one jar." 1>&2
  exit 1
fi

export SPARK_EXAMPLES_JAR

EXAMPLE_MASTER=${MASTER:-"local[*]"}

if [[ ! $EXAMPLE_CLASS == org.apache.spark.examples* ]]; then
  EXAMPLE_CLASS="org.apache.spark.examples.$EXAMPLE_CLASS"
fi

"$FWDIR"/bin/spark-submit \
  --master $EXAMPLE_MASTER \
  --class $EXAMPLE_CLASS \
  "$SPARK_EXAMPLES_JAR" \
  "$@"

