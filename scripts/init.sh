#!/bin/bash

docker compose down
docker rmi spring_messenger-back spring_messenger-db

docker compose up -d
