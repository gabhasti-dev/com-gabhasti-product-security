FROM openjdk:8-jre

MAINTAINER Marsh Dsilva , dsilvamarsh@gmail.com

ARG JAR_FILE=target/*.jar 

WORKDIR /app

COPY ${JAR_FILE} com-gabhasti-product-security.jar

ENTRYPOINT ["java", "-jar" , "com-gabhasti-product-security.jar"]

