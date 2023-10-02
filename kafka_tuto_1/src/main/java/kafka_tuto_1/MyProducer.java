package kafka_tuto_1;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;


public class MyProducer {
	public static void main(String[] args) {
		new MyProducer();
	}

	MyProducer() {
		// records sent by this producer will have no key, and a string value
		KafkaProducer<Void, String> kafkaProducer;
		// create the Kafka producer with the appropriate configuration
		kafkaProducer = new KafkaProducer<>(configureKafkaProducer());
		
		try {
			// create and sent records to the 'test' topic
			int i = 0;
			String message = null;
			while (true) {
				message = "{ \"number\": " + i++ + "}";
				kafkaProducer.send(new ProducerRecord<Void, String>("test", null, message));
				// wait 1 second
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.err.println("something went wrong... " + e.getMessage());
		} finally {
			kafkaProducer.close();
		}

	}

	private Properties configureKafkaProducer() {
		Properties producerProperties = new Properties();
		producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.VoidSerializer");
		producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");
		return producerProperties;
	}
}