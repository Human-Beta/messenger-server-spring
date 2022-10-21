#!/bin/bash

docker rm messenger-back
docker rmi messenger-back

#TODO: creds in the repo? bad idea. use from ENVs somehow
docker build -t messenger-back \
  --build-arg PROFILE=prod \
  --build-arg DB_USER=root \
  --build-arg DB_PASSWORD=admin \
  .

docker run --name messenger-back -d -p 8080:8080 -p 8888:8888 messenger-back
