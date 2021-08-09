FROM openjdk:8-alpine
ARG MX_COMMUNITY
EXPOSE 9420
ADD ${MX_COMMUNITY} /mx-community.jar
ENTRYPOINT ["java","-jar","/mx-community.jar"]