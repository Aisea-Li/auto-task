#!/bin/bash

JAR_NAME="auto-task.jar"
PID_FILE="./${JAR_NAME}.pid"

if [ -f "${PID_FILE}" ]; then
	pid=`cat ${PID_FILE}`
	kill -9 ${pid}
	rm -rf ${PID_FILE}
	echo "${JAR_NAME} Stoped!"
fi