package kafka_tuto_2;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.gson.Gson;

public class ProductDeserializer implements Deserializer<Product> {

	@Override
	public Product deserialize(String topic, byte[] data) {
		if (data == null)
			return null;
		String jsonString = new String(data);
		Gson gson = new Gson();
		return gson.fromJson(jsonString, Product.class);
	}
}
