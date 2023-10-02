package kafka_tuto_2;

import java.nio.charset.Charset;
import org.apache.kafka.common.serialization.Serializer;
import com.google.gson.Gson;

public class ProductSerializer implements Serializer<Product> {
	@Override
	public byte[] serialize(String topic, Product data) {
		if (data == null)
			return null;
		Gson gson = new Gson();
		String jsonString = gson.toJson(data);
		return jsonString.getBytes(Charset.forName("ISO-8859-1"));
	}
}