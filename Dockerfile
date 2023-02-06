FROM openjdk:11
ARG jar_file=server-0.0.1.jar
ENV JAR_FILE=$jar_file
ENV JAR_FILE_PATH=./build/libs/$jar_file
WORKDIR /app
COPY $JAR_FILE_PATH ./
EXPOSE 8080 8888
CMD java -jar $JAR_FILE