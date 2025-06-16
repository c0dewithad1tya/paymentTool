## 🧪 Kafka Hands-on Setup

### 🧰 Prerequisites

* **Docker Desktop** should be installed and running.
* A terminal (CMD, Bash, or Terminal app).

---

### ✅ Step 1: Create a Kafka Project Directory

```bash
mkdir kafka-lab
cd kafka-lab
```

---

### ✅ Step 2: Create `docker-compose.yml`

Create a file named `docker-compose.yml` with the following content:

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
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```

---

### ✅ Step 3: Launch Kafka and Zookeeper

In your terminal, run:

```bash
docker-compose up -d
```

Let it pull images and spin up the containers.

To verify:

```bash
docker ps
```

You should see 2 running containers: one for Kafka and one for Zookeeper.

---

### ✅ Step 4: Create a Kafka Topic

Get the Kafka container ID:

```bash
docker ps
```

Now exec into the container:

```bash
docker exec -it <kafka_container_id> bash
```

Once inside the Kafka container:

```bash
kafka-topics --create \
  --topic test-topic \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1
```

Then list topics:

```bash
kafka-topics --list --bootstrap-server localhost:9092
```

You should see `test-topic`.

---

### ✅ Step 5: Produce Messages

In the same Kafka container terminal:

```bash
kafka-console-producer \
  --topic test-topic \
  --bootstrap-server localhost:9092
```

Start typing messages, like:

```
Hello Kafka!
First message
Second message
```

(Each message is sent when you hit Enter)

---

### ✅ Step 6: Consume Messages

Open **another terminal** and enter the Kafka container again, then run:

```bash
kafka-console-consumer \
  --topic test-topic \
  --bootstrap-server localhost:9092 \
  --from-beginning
```

You’ll see the messages appear.

---

## 🎉 Success!

You’ve now:

* Deployed Kafka locally using Docker
* Created a topic
* Sent and received messages using CLI tools

---