## Build

### Gradle
	./gradlew clean build createDockerfile

### Docker / Docker Compose


## Commands
### init spring project
	curl https://start.spring.io/starter.tgz -d dependencies=web,data-jpa -d type=gradle-project -d baseDir=. | tar -xzvf -


### Kafka

#### Kafka (bin)

- start zookeeper ` bin/zookeeper-server-start.sh config/zookeeper.properties`
- start kafka `bin/kafka-server-start.sh config/server.properties`
- create topic `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic contact_v1_contact_new`
- start producer `bin/kafka-console-producer.sh --broker-list localhost:9092 --topic contact_v1_contact_new`
- start consumer `bin/kafka-console-consumer.sh  --bootstrap-server localhost:9092 --topic contact_v1_contact_new`

#### Kafka spotify/kafka (Docker)
##### start kafka
	docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 spotify/kafka

#### Create Topics
	bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic contact_v1_contact_new

#### Consumer
	bin/kafka-console-consumer.sh  --from-beginning --zookeeper localhost:2181 --topic  contact_v1_contact_new  --from-beginning

	bin/kafka-console-consumer.sh --bootstrap-server localhost:90092 --topic contact_v1_contact_new  --from-beginning

#### Producer
	bin/kafka-console-producer.sh --broker-list localhost:9092 --topic contact_v1_contact_new

### MySQL

	docker run -d \
    --name spring-boot-jpa-docker-webapp \
    --link demo-mysql:mysql \
    -p 8080:8080 \
    -e DATABASE_HOST=demo-mysql \
    -e DATABASE_PORT=3306 \
    -e DATABASE_NAME=demo \
    -e DATABASE_USER=dbuser \
    -e DATABASE_PASSWORD=dbp4ss \
    g00glen00b/spring-boot-jpa-docker-webapp


### Find process using port
	find pid using port (OSX):  `lsof -n -iTCP:$PORT | grep LISTEN`
