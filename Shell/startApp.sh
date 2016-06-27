#!/bin/bash

if [[ $# -eq 0 ]] ; then
    echo 'please enter paramter start|stop'
    exit 0
fi

MODE="$1"
APP_PID="runningApp.pid"

if [ "$1" == "start" ]; then
	if [ -s "$APP_PID" ]; then
		echo "@@@ It's already running...."
	else
		echo 'Starting app'
		java -jar loadSimulator-1.0.0-SNAPSHOT.jar --spring.config.location=config.properties & echo $! > runningApp.pid
	fi
elif [ "$1" == "stop" ]; then
	if [ -s "$APP_PID" ]; then
		echo 'Shutdown app'
		kill -9 $(<"$APP_PID")
		> runningApp.pid
	else
		echo '@@@App is not running.....'
	fi
else
    echo 'please enter paramter start|stop'
    exit 0	
fi
