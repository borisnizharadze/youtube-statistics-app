# youtube-statistics-app

#Requirements to run project
1. docker desktop
2. kubernetes extention enabled for doker desktop
3. minikube
4. java 11
5. maven
6. npm for building web-app

#How to build
execute buildAndDeploy.sh script for building and deploying apps to k8s cluster

#Project description

Project is devided to multiple micro-services:
1. web-app
2. api
3. producer
4. consumer
5. core
6. rabbitmq
7. redis

web-app connects to api using http (this is only access point to k8s cluster) and Stomp socket over SockJS, http is used for creating, autorizing, updating user properites and socket is used to recieve user-specific real-time messages (youtube-statistics). Actually web-app connects rabbitmq to create virtual queues which are bound to stomp topics. Core micro-service represent main communication point, has several gRPC endpoints exposed which are used by api, producer and consumer micro-services, and produces messages to rabbitmq queue which is listened by api and it resends this messages to user using stomp protocol. Producer micro-service retrieves users every minute from core for updating youtube statistic and produces messages to rabbitmq queue. Consumer consumes statistic update messages, collects youtube statistics from Youtube Data API v3 and calls core micro-service for updating statistics.

