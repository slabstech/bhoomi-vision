## Diet

#### TODO
* Create Array values of Recipes from Ammanaaduge

* docker build -t slabstech/bhoomi-garuda-diet .
* docker scan slabstech/bhoomi-garuda-diet

- Use Dockerfile to test the application
- Use GithubAction to validate against tests for all scenarios

<!-- 
@sachin - Update the build steps for Java 17, SpringBoot, MariaDB, Kafka

#### Build 
* I/P : GitHub repo with SpringBoot project and maven build tool. Lombok and Postgis docker

* Steps
    * Version Control
        * Fork the repo
          ````
          gh repo fork slabstech/bhoomi    
          ````
        * Clone the repo (Optional)
          ````
          gh repo clone slabstech/bhoomi
    
          ```` 
        * Move to required project
          ````
          cd bhoomi/garuda
          ````
        * create development branch
          ```` 
          git checkout -b "feature/sp2-test-setup-2"
          ````       
    * Maven
        * Compile the project
          ````
          mvnw clean install
          ```` 
        * Without test
          ````
          mvnw install -DskipTests 
          ````
        * Run test
          ````
          mvnw test 
          ```` 
    * Continuous Integration - Docker - [Dockerfile](https://github.com/sachinsshetty/bhoomi/blob/main/garuda/Dockerfile) and [file](https://github.com/slabstech/docker/blob/master/dockerfiles/server/postgis/Dockerfile)
        * SpringBoot via pom.xml
        * Java 17 - Custom JRE via maven jdk image
        * base alpine-linux image - postgis/postgis:14-3.3-alpine
          ````
           docker build -t slabstech/bhoomi-garuda .
          ````
        * Push image to dockerhub
          ````
           docker push slabstech/bhoomi-garuda
          ````
    * Continuous Deployment - Docker compose - [file](https://github.com/slabstech/docker/blob/master/docker_compose_files/docker-compose-springboot-postgis.yml)
        * SpringBoot
        * PostgreSQL / PostGIS
    * GitHub Actions
        * TODO
    * Verification of REST API
        * curl
        * postman
    * Create PR to upstream
      ````
      gh pr create --repo slabstech/bhoomi -f
      ````


### References
* fork repo - https://cli.github.com/manual/gh_repo_fork
    * https://docs.github.com/en/get-started/quickstart/fork-a-repo
* clone repo - https://cli.github.com/manual/gh_repo_clone
* Disable tests - https://maven.apache.org/surefire/maven-surefire-plugin/examples/skipping-tests.html
* Docker postgis -  postgis/postgis:14-3.3-alpine  from https://registry.hub.docker.com/r/postgis/postgis/
* https://medium.datadriveninvestor.com/getting-started-building-location-based-gis-rest-apis-with-java-ca28a8869af3
* https://hub.docker.com/r/windriver/wrlx-image


---


## Build

In order to simplify the most commonly used maven commands, a `Makefile` is provided

```
make
```

will build the project, apply code formatting rules, run tests, and build the docker image

Separate make targets exist for these tasks, to be run with `make <target>` (e.g. `make install`):

* `all`: runs `format install test build-image`
* `lint`: fails if any java source code file or pom.xml file are ill-formatted
* `format`: applies formatting rules to all java and pom.xml files
* `install`: builds and installs the project artifacts without running tests
* `test`: runs unit and integration tests
* `build-image`: builds the docker image

-->