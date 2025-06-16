package dev.ad1tya.kafka;

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
