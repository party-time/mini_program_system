#!/usr/bin/env bash

#生成密钥
openssl req -newkey rsa:4096 -nodes -sha256 -keyout certs/lpxxn.com.key -x509 -days 365 -out certs/lpxxn.com.crt

#启动私服
docker run -d -p 5000:5000 --restart=always --name registry_https -v `pwd`/certs:/home/liuwei/certs -e REGISTRY_HTTP_TLS_CERTIFICATE=/home/liuwei/certs/lwdocker.com.crt -e REGISTRY_HTTP_TLS_KEY=/home/liuwei/certs/lwdocker.com.key registry:2
docker run -d -p 5000:5000  -v `pwd`/dockerregister:/var/lib/registry  --restart=always --name registry_https -v `pwd`/certs:/home/certs -e REGISTRY_HTTP_TLS_CERTIFICATE=/home/certs/lpxxn.com.crt -e REGISTRY_HTTP_TLS_KEY=/home/certs/lpxxn.com.key registry:2

#mac添加证书
sudo security add-trusted-cert -d -r trustRoot -k /Library/Keychains/System.keychain ~/new-root-certificate.crt
