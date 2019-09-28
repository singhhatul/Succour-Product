FROM openjdk:11

ADD ./target/authentication-service-0.0.1-SNAPSHOT.jar /usr/src/authentication-service-0.0.1-SNAPSHOT.jar

WORKDIR usr/src

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.5.1/wait /wait
RUN chmod +x /wait
CMD /wait && java -jar authentication-service-0.0.1-SNAPSHOT.jar