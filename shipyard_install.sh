#!/usr/bin/env bash

docker-machine create -d virtualbox shipyardMachine

eval $(docker-machine env shipyardMachine)

#shipyard需要的数据库
docker run \
    -ti \
    -d \
    -v /Users/administrator/liuwei/shipyardData:/data \
    --restart=always \
    --name shipyard-rethinkdb \
    rethinkdb

#discovery
docker run -d \
  --name consul \
  -p "8500:8500" \
  -h "consul" \
  consul agent -server -bootstrap -client "0.0.0.0"

#shipyard-proxy
docker run \
    -ti \
    -d \
    -p 2375:2375 \
    --hostname=$HOSTNAME \
    --restart=always \
    --name shipyard-proxy \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -e PORT=2375 \
    shipyard/docker-proxy:latest

#swarm-manager ip地址是discovery的ip
docker run \
    -ti \
    -d \
    --restart=always \
    --name shipyard-swarm-manager \
    swarm:latest \
    manage --host tcp://0.0.0.0:3375 consul://192.168.99.106:8500

#shipyard-swarm-agent
docker run \
    -ti \
    -d \
    --restart=always \
    --name shipyard-swarm-agent \
    swarm:latest \
    join --addr 192.168.99.106:2375 consul://192.168.99.106:8500

#shipyard-controller 初始用户名:admin 密码:shipyard
docker run \
    -ti \
    -d \
    --restart=always \
    --name shipyard-controller \
    --link shipyard-rethinkdb:rethinkdb \
    --link shipyard-swarm-manager:swarm \
    -p 8080:8080 \
    shipyard/shipyard:latest \
    server \
    -d tcp://swarm:3375

#启动registrator
docker run -d \
--restart=always \
--name=registrator \
--net=host \
-v /var/run/docker.sock:/tmp/docker.sock gliderlabs/registrator \
consul://192.168.99.106:8500

#自动添加node
curl -sSL https://shipyard-project.com/deploy | ACTION=node DISCOVERY=consul://192.168.99.106:8500 bash -s

#启动consul server
nohup consul agent -server -bootstrap -data-dir /opt/consul -bind=192.168.1.25 -client=0.0.0.0 &

#启动consul client 加入server
nohup consul agent -data-dir /opt/consul -bind=192.168.1.21 &
consul join 192.168.1.25


