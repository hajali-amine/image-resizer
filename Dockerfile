# initialize build and set base image for first stage
FROM maven:3.6.3-adoptopenjdk-11 as build
# speed up Maven JVM a bit
# setting an environment variable that makes the maven JVM perform the basic just-in-time compilation of the code
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
# set working directory
WORKDIR /imagecompressor/build
# copy just pom.xml
COPY pom.xml .
# go-offline using the pom.xml
RUN mvn dependency:go-offline
# copy your other files
COPY ./src ./src
# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true

# -------------------------------------------------

# set base image for second stage
FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
# set deployment directory
WORKDIR /imagecompressor
# expose 8080 port
EXPOSE 8080
# take aws access key as env variable
ENV AWS_ACCESS_KEY=key
# take aws secret key as env variable
ENV AWS_SECRET_KEY=secret
# copy over the built artifact from the maven image
COPY --from=build /imagecompressor/build/target/image.compressor.jar ./target/image.compressor.jar
# make the necessary directories for the project
RUN mkdir images resizedimages retrieve
# run jar
CMD java -jar ./target/image.compressor.jar