# demo-apache-kafka

[![Java](https://img.shields.io/badge/java-17-blue)](https://www.oracle.com/java/)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka%20Confluentic-7.4-orange)](https://kafka.apache.org/)
[![License: MIT](https://img.shields.io/badge/license-MIT-green)](./LICENSE)
[![Docker Compose](https://img.shields.io/badge/Docker%20Compose-3.8-blue?logo=docker&logoColor=white)](https://docs.docker.com/compose/)

Functional demo with Spring Boot and Apache Kafka

> Note: this project uses the Confluent Docker image to avoid installing Apache Kafka locally.
---

## Todo

- [x] Use KRaft instead of Zookeeper 
- [x] Add UI
- [ ] Add Retry + DLT
- [ ] Add tests
- [ ] Run the proejct via mvn commands
---

## Notable features

1. **Modular architecture** – Producer and consumer are separate, following single responsibility principle.

2. **Kafka UI** – A web interface to view and manage all Kafka topics, messages, and consumer groups.

3. **Asynchronous Kafka producer with callbacks** – Logs success and errors efficiently without blocking.

4. **Error handling for consumers** – ErrorHandlingDeserializer prevents malformed messages from crashing the service.

5. **Concurrent message processing** – Consumers can process multiple messages in parallel for scalability.

6. **Programmatic topic creation** – Topics are defined in code using NewTopic and TopicBuilder.

---

## How to use it



40s demo (literal)

From the project root:


1. **Start Kafka (docker)**:
   ```bash
   docker compose up -d
   ```
2. **Build and run**:
   ```
   Run Main from the modules:

   - Producer

   - Consumer
   ```
3. **Send a test message**:

   Post request to: `http://localhost:8084/event`

   body: `(raw)`

   ```
   {
    "transactionID": 1,
    "event": "WITHDRAW",
    "amount": 100.0,
    "status": "SUBMITTED"
   }
   ```

