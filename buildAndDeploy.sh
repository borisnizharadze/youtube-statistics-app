#!/bin/bash

cd protocols
cd core-protocols
mvn clean install
cd ..
cd ..

cd producer
mvn clean install
docker build -t bnizh/producer .
minikube image load bnizh/producer
cd ..

cd api
mvn clean install
docker build -t bnizh/api .
minikube image load bnizh/api
cd ..

cd core
mvn clean install
docker build -t bnizh/core .
minikube image load bnizh/core
cd ..

cd consumer
mvn clean install
docker build -t bnizh/consumer .
minikube image load bnizh/consumer
cd ..

cd k8s
./run_k8s_cluster.sh

docker image prune --filter "dangling=true" --force