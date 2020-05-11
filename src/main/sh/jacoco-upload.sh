#!/bin/bash
FILE=$1
RUN_NAME=$2
SERVER=$3
curl -F "file=@${FILE}" -F "runName=${RUN_NAME}" -H 'enctype: multipart/form-data' http://${SERVER}/jacocofile
