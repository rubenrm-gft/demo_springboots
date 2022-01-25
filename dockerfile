FROM openjdk:11
WORKDIR /opt/demo
COPY build/libs/demo-0.0.1-SNAPSHOT-plain.jar ./
CMD java -jar demo-0.0.1-SNAPSHOT-plain.jar