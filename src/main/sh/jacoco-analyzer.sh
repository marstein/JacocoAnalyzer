#!/bin/bash

# arg2: file name of Jacoco XML
importOneJacocoXmlFile() {
  BASEDIR=$(dirname "$0")
  java -jar $BASEDIR/../../libs/redlock-jacoco-analyzer-0.1.0.jar -r "${runName}" -f "$1"
}

runModuleTests() {
  mvn test
  importOneJacocoXmlFile target/site/jacoco/jacoco.xml
}

runRqlModuleTests() {
  >~/.variables
  ( cd ~/redlock-platform/redlock-db/tools/ || exit; ./dev-util.sh -t 20 -v )
  source ~/.variables
  cd ~/redlock-platform/redlock-java/redlock-maven-agg/ || exit
  for m in redlock-search redlock-rql redlock-public-rest-api
#  for m in redlock-search
  do
    ( cd $m || exit; runModuleTests )
  done
}

runName="$1"
if [ -z "$runName" ]; then echo 2>&1 "usage: $0 run-name"; fi

echo "Jacoco Analyzer"
echo Please remember to have docker started
runRqlModuleTests
