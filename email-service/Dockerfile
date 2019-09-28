FROM openjdk:11

ADD ./target/email-service-0.0.1-SNAPSHOT.jar /usr/src/email-service-0.0.1-SNAPSHOT.jar

WORKDIR usr/src

## Add the wait script to the image
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.5.1/wait /wait
RUN chmod +x /wait
#ENTRYPOINT ["/wait","java","-jar","track-service-0.0.1-SNAPSHOT.jar"]
CMD /wait && java -jar email-service-0.0.1-SNAPSHOT.jar