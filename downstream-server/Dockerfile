FROM openjdk:11

WORKDIR /user/src

ADD ./target/downstream-server-0.0.1-SNAPSHOT.jar /user/src/downstream-server-0.0.1-SNAPSHOT.jar

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.5.1/wait /wait

RUN chmod +x /wait

CMD /wait && java -jar downstream-server-0.0.1-SNAPSHOT.jar
