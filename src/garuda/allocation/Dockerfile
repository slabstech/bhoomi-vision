FROM maven:3.8.6-eclipse-temurin-17-alpine AS jre-build
RUN mkdir /app
WORKDIR /app

#COPY . /app

#RUN mvn clean package -DskipTests
COPY target/garuda-0.0.1-SNAPSHOT.jar app.jar

# TODO do this step only if pom.xml is modified. i.e new java modules are added. Add conditional block

RUN jdeps --ignore-missing-deps --module-path modules --add-modules=ALL-MODULE-PATH --generate-module-info out /app/app.jar && jlink --add-modules ALL-MODULE-PATH --no-man-pages --no-header-files --compress=2 --output jre

# take a smaller runtime image for the final output
FROM alpine:3.17.0 as bhoomi-garuda
RUN apk --no-cache add dumb-init && mkdir /app && addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
WORKDIR /app

# copy the custom JRE produced from jlink
COPY --from=jre-build /app/jre jre

# copy the app
COPY --from=jre-build /app/app.jar app.jar
RUN chown -R javauser:javauser /app
USER javauser
EXPOSE 8080

ENTRYPOINT ["dumb-init", "jre/bin/java", "-jar", "app.jar"]
