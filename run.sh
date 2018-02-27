#!/usr/bin/env bash

mvn clean install

# Export the active docker machine IP
export DOCKER_IP=$(docker-machine ip $(docker-machine active))

# Remove existing containers
docker-compose stop
docker-compose rm -f

docker images | grep none | awk '{print $3}' | xargs docker rmi

# Start the discovery service next and wait
docker-compose up -d eureka-server

while [ -z ${EUREKA_SERVER_READY} ]; do
  echo "Waiting for eureka server..."
  if [ "$(curl --silent $DOCKER_IP:9999/actuator/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      EUREKA_SERVER_READY=true;
  fi
  sleep 2
done

# Start the config service first and wait for it to become available
docker-compose up -d config-server

while [ -z ${CONFIG_SERVER_READY} ]; do
  echo "Waiting for config server..."
  if [ "$(curl --silent $DOCKER_IP:9998/actuator/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      CONFIG_SERVER_READY=true;
  fi
  sleep 2
done



# Start the other containers
docker-compose up -d

# Attach to the log output of the cluster
docker-compose

docker stack deploy -c docker-stack.yml vote

