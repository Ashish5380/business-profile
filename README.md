# Business Profile
Repository for central business-profile service

## Pre requisite
* Kafka
* Redis
* Mongo DB
* Maven
* Docker
* Docker Compose CLI

## Setup
* Run Kafka
* Run Redis
* Run Mongo DB
```yaml
version: "3.9"
services:
  redis:
    image: 'bitnami/redis:latest'
    container_name: redis
    restart: always
    ports:
     - 6379:6379
    environment:
     - ALLOW_EMPTY_PASSWORD=yes
  mongo:
    image: mongo
    container_name: mongo
    restart: always
    ports:
     - 27017:27017
  mongo-express:
    image: mongo-express
    container_name: mongo-express
    restart: always
    ports:
      - 8081:8081
  zookeeper:
    image: 'bitnami/zookeeper:latest'
    container_name: zookeeper
    ports:
      - 2181:2181
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: 'bitnami/kafka:latest'
    container_name: kafka
    ports:
      - 9092:9092
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_LISTENERS=PLAINTEXT://:9092
      - KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://127.0.0.1:9092
      - KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - zookeeper
```

* Copy the above into a file named `docker-compose.yml`. Start required applications using.
```shell
$ docker compose up -d
```
* Clone the code
```shell
git clone https://github.com/Ashish5380/business-profile.git
```
* Start sever with 
```shell
mvn clean spring-boot:run -DskipTests
```
This will start the server at `localhost:9015/`. You can validate server is running using :
```shell
curl --location 'localhost:9015/ping'
```
## API's
* Create your first business-profile
```shell
curl --location 'localhost:9015/business-profile/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "companyName": "ABC Company",
    "legalName": "ABC Legal Name",
    "businessAddress": {
        "line1": "123 Main Street",
        "city": "New York",
        "state": "NY",
        "pinCode": "10001",
        "country": "USA"
    },
    "legalAddress": {
        "line1": "456 Legal Avenue",
        "city": "Los Angeles",
        "state": "CA",
        "pinCode": "90001",
        "country": "USA"
    },
    "email": "info@abccompany.com",
    "website": "www.abccompany.com",
    "taxIdentifier": {
        "type": "EIN",
        "identifier": "123456787"
    }
}'
```
* Create product information that consumes business-profile information
```shell
curl --location 'localhost:9015/product/create' \
--header 'Content-Type: application/json' \
--data '{
    "externalProductId" : "<External-Product-Identifier>",
    "name" : "Quickbook",
    "description" : "Quickbooks for accounting"
}'
```
* Subscribe to existing business-profile
```shell
curl --location 'localhost:9015/subscription/create' \
--header 'Content-Type: application/json' \
--data '{
    "externalProductId" : "<External-Product-Identifier>",
    "businessProfileId" : "<Business-Profile-Identifier>"
}'
```
* Update business-profile information
```shell
curl --location --request PUT 'localhost:9015/business-profile/<Business-Profile-Identifier>?externalProductId=<External-Product-Identifier>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "companyName": "ABC Company",
    "legalName": "ABC Legal Name",
    "businessAddress": {
        "line1": "123 Main Street",
        "city": "New York",
        "state": "NY",
        "pinCode": "10001",
        "country": "USA"
    },
    "legalAddress": {
        "line1": "456 Legal Avenue",
        "city": "Los Angeles",
        "state": "CA",
        "pinCode": "90001",
        "country": "USA"
    },
    "email": "info@abccompany.com",
    "website": "www.abccompany.com",
    "taxIdentifier": {
        "type": "EIN",
        "identifier": "123456781"
    }
}'
```
* Get business-profile information with identifier and product identfier
```shell
curl --location 'localhost:9015/business-profile/<Business-Profile-Identifier>?externalProductId=<Product-Identifier>'
```

