
git pull

mvn clean package -Dmaven.test.skip=true

mv ./target/auto-task.jar /app/auto-task/auto-task.jar

mv ./run.sh /app/auto-task/run.sh

cd /app/auto-task/

sh run.sh