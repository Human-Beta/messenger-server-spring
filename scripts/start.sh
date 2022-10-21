#!/bin/bash

if [[ $PROFILE == 'prod' ]]; then
  ./start.prod.sh
else
  ./start.local.sh
fi
