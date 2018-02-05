#创建docker发现中心
docker-machine create -d virtualbox mh-keystore

eval "$(docker-machine env mh-keystore)"

#使用consul当注册中心，也可以使用zookeeper
docker run -d --name consul -p "8500:8500" -h "consul" consul agent -server -bootstrap -client "0.0.0.0"

#创建swarm manager
docker-machine create \
-d virtualbox \
--swarm --swarm-master \
--swarm-discovery="consul://192.168.1.25:8500" \
--engine-opt="cluster-store=consul://192.168.1.25:8500" \
--engine-opt="cluster-advertise=eth1:2376" \
swarm-master

#创建本地swarm node
docker-machine create -d virtualbox \
  --swarm \
  --swarm-discovery="consul://192.168.1.25:8500" \
  --engine-opt="cluster-store=consul://192.168.1.25:8500" \
  --engine-opt="cluster-advertise=eth1:2376" \
  ytf13

#创建本地局域网其他主机 cluster-advertise对应的网卡需要是内网通讯网卡
docker-machine create -d generic \
  --generic-ip-address=192.168.1.25 \
  --generic-ssh-key ~/.ssh/id_rsa \
  --swarm \
  --swarm-discovery="consul://$(docker-machine ip mh-keystore):8500" \
  --engine-opt="cluster-store=consul://$(docker-machine ip mh-keystore):8500" \
  --engine-opt="cluster-advertise=ens33:2376" \
  ytf25


#创建overlay网络
eval $(docker-machine env --swarm mhs-demo0)

docker network create --driver overlay --subnet=10.0.9.0/24 my-net

#加入到swarm
docker swarm join \
    --token SWMTKN-1-5hjrkzo51mjjdtpup4ox84gw04a4i97f67g2j1nft58pcbvlf3-5lofhshthx92rrnh9tzgcim7e \
    192.168.1.25:2377