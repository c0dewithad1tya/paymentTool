## 🧠 **Day 1: Kafka Basics — Concepts, Architecture, and Setup**

### 🎯 Learning Objectives

By the end of today, you should be able to:

* Understand what Kafka is and where it's used.
* Explain key Kafka components (Topics, Brokers, Partitions, Zookeeper/KRaft).
* Set up a local Kafka cluster using Docker.
* Create a topic and produce/consume messages.

---

## 📘 **1. What is Apache Kafka?**

Apache Kafka is a **distributed event streaming platform** capable of handling trillions of events a day.

It acts as a **high-throughput, low-latency** messaging system ideal for:

* Real-time data pipelines
* Streaming analytics
* Decoupling systems (producer/consumer)
* Event-driven architectures

---

## ⚙️ **2. Kafka Core Concepts**

| Concept               | Description                                                    |
| --------------------- | -------------------------------------------------------------- |
| **Producer**          | Sends messages to Kafka topics.                                |
| **Consumer**          | Reads messages from Kafka topics.                              |
| **Broker**            | Kafka server. A Kafka cluster is a group of brokers.           |
| **Topic**             | Named stream of data (like a queue or feed).                   |
| **Partition**         | Topics are split into partitions for parallelism.              |
| **Offset**            | Position of a message in a partition.                          |
| **Consumer Group**    | A group of consumers sharing the work.                         |
| **Zookeeper / KRaft** | Coordination service for Kafka (KRaft is replacing Zookeeper). |

---

## 🧰 **3. Hands-on: Kafka Setup Using Docker**

Let’s get Kafka running locally in under 5 minutes using Docker.

### 🔧 Step-by-step

1. **Install Docker Desktop** (if not already installed): [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)

2. **Create a `docker-compose.yml` file**:

```yaml
version: '2'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```

3. **Run the cluster**:

```bash
docker-compose up -d
```

4. **Check if it’s running**:

```bash
docker ps
```

---

## 🧪 **4. Create Topics and Send Messages (CLI)**

### 🧵 Create a topic:

```bash
docker exec -it <container_id_of_kafka> kafka-topics --create \
  --topic test-topic --bootstrap-server localhost:9092 \
  --partitions 1 --replication-factor 1
```

### ✉️ Produce messages:

```bash
docker exec -it <kafka_container> kafka-console-producer \
  --topic test-topic --bootstrap-server localhost:9092
```

Type your messages and press `Enter`.

### 📬 Consume messages:

```bash
docker exec -it <kafka_container> kafka-console-consumer \
  --topic test-topic --bootstrap-server localhost:9092 \
  --from-beginning
```

---

## 🧠 **5. Review Questions**

1. What is the difference between a topic and a partition?
2. Why do we need Zookeeper (or KRaft)?
3. What is the purpose of consumer groups?

---

## ✅ Homework / Optional Exploration

* Try producing messages from one terminal and consuming from another in real-time.
* Explore what happens if you restart a consumer.
* Try creating a topic with more partitions.