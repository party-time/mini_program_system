FROM anapsix/alpine-java:8
VOLUME /tmp
ADD config-server-0.0.1-SNAPSHOT.jar app.jar
ADD wait-for-eureka.sh wait-for-eureka.sh
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]