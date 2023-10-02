package kafka_tuto_2;

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
		// records sent by this producer will have no key, and a Product value
		KafkaProducer<Void, Product> kafkaProducer;
		// create the Kafka producer with the appropriate configuration
		kafkaProducer = new KafkaProducer<Void, Product>(configureKafkaProducer());
		try {
			// create and sent records to the 'test' topic
			int i = 0;
			Product product = null;
			String[] names = {"banana", "apple", "carrot", "juice", "pen", "soap", "cake"};
			while (true) {
				product = new Product(names[(int) (Math.random()*names.length)], (int) (Math.random()*10));
				kafkaProducer.send(new ProducerRecord<Void, Product>("test", null, product));
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
				ProductSerializer.class);
		return producerProperties;
	}
}

