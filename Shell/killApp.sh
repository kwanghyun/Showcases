#!/bin/bash


APP_PID="app.pid"
kill -9 $(<"$APP_PID")
