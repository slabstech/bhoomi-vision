all: format install test docker

lint:
	./mvnw sortpom:verify fmt:check -ntp

format:
	./mvnw sortpom:sort fmt:format -ntp

install:
	./mvnw clean install -DskipTests -ntp -T4

test:
	./mvnw verify -ntp -T4

build-image: docker build -t slabstech/bhoomi-garuda .