FROM registry.access.redhat.com/ubi8/openjdk-11
ENV PROFILE=prod
ADD target/*.jar app.jar
EXPOSE 8090
CMD java -jar app.jar
