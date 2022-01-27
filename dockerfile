FROM openjdk:11 AS base
WORKDIR /opt/demo
COPY ./ ./
RUN ./gradlew assemble

FROM openjdk:18-jdk-alpine
WORKDIR /opt/demo
COPY --from=base /opt/demo/build/libs/demo-0.0.1-SNAPSHOT.jar ./
CMD java -jar demo-0.0.1-SNAPSHOT.jar