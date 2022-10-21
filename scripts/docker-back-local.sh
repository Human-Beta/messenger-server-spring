#!/bin/bash

docker rm messenger-back
docker rmi messenger-back

docker build -t messenger-back \
  --build-arg DB_USER=root \
  --build-arg DB_PASSWORD=admin \
  .

docker run --name messenger-back -d -p 8080:8080 -p 8888:8888 messenger-back