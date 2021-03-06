#!/usr/bin/env bash

set -e

host="$1"
shift
cmd="$@"

while [ -z ${EUREKA_SERVER_READY} ]; do
  echo "Waiting for eureka server..."
  if [ "$(curl --silent $host:9999/actuator/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      EUREKA_SERVER_READY=true;
  fi
  sleep 2
done

exec $cmd
