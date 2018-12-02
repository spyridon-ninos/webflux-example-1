# webflux-example-1
A simple webflux/kafka spring project

# step by step instructions:

- You build it using `mvn clean package`
- You run it using `java -jar `
- You need to have a kafka running at `localhost:9092`
- You run:

```
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first
```
- You send to the `localhost:8080/person` using POST a json with `first_name`, `last_name` and `dob` with ISO8601 format (e.g. 2018-12-02)
- You observe from the kafka console client the message stored in the `first` kafka topic

