#!/bin/bash

mvn clean package

docker build -t diddly-doodly-servlets .

rm -rf target

docker run -p 80:8080 diddly-doodly-servlets
