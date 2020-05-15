# JacocoAnalyzer

##How to deploy
gradle build
Should build docker image

After big jar has been created, create image

`docker build --tag marstein65/jacocoanalyzer:latest .`

Run container as demon

`docker-compose up -d`

#Rest server
Upload XML file

`for i in  ~/redlock-platform/redlock-java/redlock-maven-agg/redlock-*/target/site/jacoco/jacoco.xml
 do curl -F "file=@$i" -F 'runName=RUN2' -H 'enctype: multipart/form-data' http://localhost:8080/jacocofile&
 done`
 
Delete all method coverages, can select run

`DELETE curl -X DELETE 'http://localhost:8080/coverage_run?run=*'`