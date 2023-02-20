#!/bin/bash

kubectl delete deployment api
kubectl delete deployment consumer
kubectl delete deployment producer
kubectl delete deployment core
kubectl delete deployment rabbitmq
kubectl delete deployment redis

echo "Deploying RabbitMQ to the cluster..."
kubectl apply -f configs/rabbitmq.yml

echo "Waiting for the RabbitMQ pod to become ready..."
kubectl wait --for=condition=Ready pod -l app=rabbitmq

echo "Deploying Redis to the cluster..."
kubectl apply -f configs/redis.yml

echo "Waiting for the Redis pod to become ready..."
kubectl wait --for=condition=Ready pod -l app=redis

echo "Deploying the core micro-services to the cluster..."
kubectl apply -f configs/core.yml

echo "Waiting for the core to become ready..."
kubectl wait --for=condition=Ready pod -l app=core

echo "Deploying the api micro-services to the cluster..."
kubectl apply -f configs/api.yml

echo "Waiting for the api to become ready..."
kubectl wait --for=condition=Ready pod -l app=api

echo "Deploying the producer micro-services to the cluster..."
kubectl apply -f configs/producer.yml

echo "Waiting for the producer to become ready..."
kubectl wait --for=condition=Ready pod -l app=producer

echo "Deploying the consumer micro-services to the cluster..."
kubectl apply -f configs/consumer.yml

echo "Waiting for the consumer to become ready..."
kubectl wait --for=condition=Ready pod -l app=consumer

echo "The cluster is now running. Use 'kubectl get pods' to see the status of the pods."
