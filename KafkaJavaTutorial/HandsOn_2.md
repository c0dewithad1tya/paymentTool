Here’s what’s next in today’s hands-on (Part 2):

---

## 👨‍💻 Part 2: Kafka Java Producer & Consumer (Using Core Java)

### 🧰 Prerequisites

* JDK 17+
* Maven
* Kafka and Zookeeper running (already done)
* Your favorite IDE (IntelliJ is great for this)

---

### ✅ Step 1: Create a Maven Project

You can create a Maven project named `kafka-java-demo`.

`pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>dev.ad1tya.kafka</groupId>
    <artifactId>kafka-java-demo</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka-clients</artifactId>
            <version>3.6.1</version>
        </dependency>
    </dependencies>
</project>
```

---

### ✅ Step 2: Create a Kafka Producer

`SimpleProducer.java`:

```java
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class SimpleProducer {
    public static void main(String[] args) {
        String topicName = "test-topic";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 1; i <= 5; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, "msg-key-" + i, "Message #" + i);
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.printf("Sent to %s partition %d with offset %d%n",
                            metadata.topic(), metadata.partition(), metadata.offset());
                } else {
                    exception.printStackTrace();
                }
            });
        }

        producer.close();
    }
}
```

---

### ✅ Step 3: Create a Kafka Consumer

`SimpleConsumer.java`:

```java
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) {
        String topicName = "test-topic";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Received: key=%s value=%s partition=%d offset=%d%n",
                        record.key(), record.value(), record.partition(), record.offset());
            }
        }
    }
}
```

---

## 🧪 To Run:

1. Start your Kafka + Zookeeper (already running).
2. Run `SimpleProducer` → sends 5 messages.
3. Run `SimpleConsumer` → listens and prints those messages.

---