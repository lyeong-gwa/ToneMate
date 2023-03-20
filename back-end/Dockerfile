FROM gradle:jdk11
LABEL name = "tonemate"
LABEL email = "wjdgmll77@naver.com"
COPY ./tonemate/build/libs/tonemate-0.0.1-SNAPSHOT.jar app.jar
CMD java -jar app.jar
#CMD chmod +x ./gradlew && ./gradlew bootJar && java -jar ./build/libs/backend-0.0.1-SNAPSHOT.jar
