#!/usr/bin/env bash

cmd="$@"

while [ -z ${CONFIG_SERVER_READY} ]; do
  echo "Waiting for config server..."
  if [ "$(curl --silent $MASTER_IP:9998/actuator/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      CONFIG_SERVER_READY=true;
  fi
  sleep 2
done

exec $cmd