#!/bin/bash
FILE=${1:-src/test/resources/sample.xml}
RUN_NAME=${2:-"$(date)"}
SERVER=${3:-localhost:8080}
echo "Uploading $FILE to $SERVER for run $RUN_NAME"
curl -F "file=@${FILE};type=text/xml" -F "runName=${RUN_NAME}" -H 'enctype: multipart/form-data' http://${SERVER}/jacocofile
