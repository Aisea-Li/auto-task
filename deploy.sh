#!/bin/bash

git pull

mvn clean package -Dmaven.test.skip=true

if [ -f "/app/auto-task/stop.sh" ]
then
    sh /app/auto-task/stop.sh
fi

rm -rf /app/auto-task/

mkdir /app/auto-task/

mv ./target/auto-task.jar /app/auto-task/auto-task.jar

mv ./start.sh /app/auto-task/start.sh

mv ./stop.sh /app/auto-task/stop.sh

cd /app/auto-task/

sh run.sh