cd protocols\core-protocols
mvn clean install
echo test
cd ..
echo test2

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
minikube.exe image load bnizh/core
cd ..

cd consumer
mvn clean install
docker build -t bnizh/consumer .
minikube.exe image load bnizh/consumer@echo off

cd protocols
cd core-protocols
echo Building core-protocols...
mvn clean install
echo.
cd ..
cd ..

cd producer
echo Building producer...
mvn clean install
echo Building Docker image for producer...
docker build -t bnizh/producer .
echo Loading producer image into Minikube...
minikube.exe image load bnizh/producer
echo.
cd ..

cd api
echo Building api...
mvn clean install
echo Building Docker image for api...
docker build -t bnizh/api .
echo Loading api image into Minikube...
minikube.exe image load bnizh/api
echo.
cd ..

cd core
echo Building core...
mvn clean install
echo Building Docker image for core...
docker build -t bnizh/core .
echo Loading core image into Minikube...
minikube.exe image load bnizh/core
echo.
cd ..

cd consumer
echo Building consumer...
mvn clean install
echo Building Docker image for consumer...
docker build -t bnizh/consumer .
echo Loading consumer image into Minikube...
minikube.exe image load bnizh/consumer
echo.
cd ..

cd k8s
echo Running Kubernetes cluster...
run_k8s_cluster.bat
echo.
echo Pruning Docker images...
docker image prune --filter "dangling=true" --force
echo.

pause

cd ..

cd k8s
run_k8s_cluster.bat

docker image prune --filter "dangling=true" --force
