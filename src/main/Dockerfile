FROM bellsoft/liberica-openjdk-alpine:latest
COPY ./java ./src

RUN mkdir ./out
RUN javac -sourcepath ./src -d out ./src/sample/App.java


