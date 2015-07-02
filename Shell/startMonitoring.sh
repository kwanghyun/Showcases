#!/bin/bash
# Monitor scripts 

INTERVAL=5
TODAY=$(date +"%Y-%m-%d")
echo Welcome to Performance monitor scripts.... 
echo Monitoring interval ::  $INTERVAL

vmstat 5 | awk '{ print strftime("%Y-%m-%d %H:%M:%S"), $0; fflush(); }' > "memory.log" &
mpstat -P ALL 5 | awk '{ print strftime("%Y-%m-%d %H:%M:%S"), $0; fflush(); }' > "cpu.log" &
iostat -xm 5 | awk '{ print strftime("%Y-%m-%d %H:%M:%S"), $0; fflush(); }' > "disk.log" &

echo Monitoring...................

