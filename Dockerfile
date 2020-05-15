FROM adoptopenjdk/openjdk11
LABEL maintainer=mastein@paloaltonetworks.com
RUN groupadd spring
RUN useradd -ms /bin/bash spring -g spring
#RUN addgroup spring && adduser spring --ingroup spring
USER spring:spring
ARG JAR_FILE=build/libs/JacocoAnalyzer.*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "-w"]
