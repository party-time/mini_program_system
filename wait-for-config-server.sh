#!/usr/bin/env bash

host="$1"
shift
cmd="$@"

while [ -z ${CONFIG_SERVER_READY} ]; do
  echo "Waiting for config server..."
  if [ "$(curl --silent $host:9998/actuator/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      CONFIG_SERVER_READY=true;
  fi
  sleep 2
done

exec $cmd