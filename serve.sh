#!/bin/bash

if [ ! -d "/opt/tomcat/webapps" ]; then
  echo "ERROR: Apache Tomcat Not Found"

  exit 1
fi

mvn clean package

if [ ! -d "target" ]; then
  echo "ERROR: Cannot compile java project"

  exit 1
fi

sudo rm -rf /opt/tomcat/webapps/*

sudo mv target/diddly-doodly-servlets.war /opt/tomcat/webapps/

rm -rf target

sudo systemctl restart tomcat
