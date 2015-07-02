#!/bin/bash

ps -ef | grep vmstat | awk '{print $2}' | xargs kill
echo Killing VMSTAT_PID :: $VMSTAT_PID
#kill $VMSTAT_PID

ps -ef | grep iostat | awk '{print $2}' | xargs kill
echo Killing IOSTAT_PID :: $IOSTAT_PID
#kill $IOSTAT_PID

ps -ef | grep mpstat | awk '{print $2}' | xargs kill
echo Killing MPSTAT_PID :: $MPSTAT_PID
#kill $MPSTAT_PID

echo DONE
