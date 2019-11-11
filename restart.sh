#!/bin/bash

#get source
sudo git pull

#build package
#if want skip test,run： sudo mvn clean package -Dmaven.test.skip=true
sudo mvn clean package -Dmaven.test.skip=true

#search PID
PID=$(ps -ef | grep 'sudo java -jar target/minio-0.0.1-SNAPSHOT.jar' | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo Application is already stopped
else
    #if exist old process, kill it
    echo kill $PID
    sudo kill $PID
fi

#restart the process，redirect log to /var/log/serrhub.log，& put the process in the background
sudo java -jar target/minio-0.0.1-SNAPSHOT.jar > /var/log/minio.log &
