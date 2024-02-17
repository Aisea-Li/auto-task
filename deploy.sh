#!/bin/bash

git pull

mvn clean package -Dmaven.test.skip=true

mv ./target/auto-task.jar /app/auto-task/auto-task.jar

mv ./start.sh /app/auto-task/start.sh

cd /app/auto-task/

sh run.sh