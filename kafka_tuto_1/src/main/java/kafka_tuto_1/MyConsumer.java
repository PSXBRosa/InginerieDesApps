package kafka_tuto_1;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MyConsumer {
	
	public static void main(String[] args) {
		new MyConsumer();
	}
	
	MyConsumer() {
		KafkaConsumer<Void, String> kafkaConsumer;
		kafkaConsumer = new KafkaConsumer<>(configureKafkaConsumer());
		kafkaConsumer.subscribe(Collections.singletonList("test"));
		// reads from the topic
		try {
			Duration timeout = Duration.ofMillis(1000);
			ConsumerRecords<Void, String> records = null;
			while (true) { // I'm a machine, I can work forever :-)
				records = kafkaConsumer.poll(timeout);
				for (ConsumerRecord<Void, String> record : records)
					System.out.println(record.value());
			}
		} catch (Exception e) {
			System.err.println("something went wrong... " + e.getMessage());
		} finally {
			kafkaConsumer.close();
		}
	}
	
	private Properties configureKafkaConsumer() {
		Properties consumerProperties = new Properties();
		consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.VoidDeserializer");
		consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // from beginning
		consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "myOwnPrivateJavaGroup");
		return consumerProperties;
	}

}
