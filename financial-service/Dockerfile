FROM openjdk:11

ADD ./target/financial-service-0.0.1-SNAPSHOT.jar /usr/src/financial-service-0.0.1-SNAPSHOT.jar

WORKDIR usr/src

EXPOSE 8089

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.5.1/wait /wait

RUN chmod +x /wait

CMD /wait && java -jar financial-service-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar","financial-adapter-0.0.1-SNAPSHOT.jar"]