FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/JacocoAnalyzer.*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "-w"]
