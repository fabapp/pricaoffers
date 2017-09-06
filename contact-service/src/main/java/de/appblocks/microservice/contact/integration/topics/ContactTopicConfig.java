package de.appblocks.microservice.contact.integration.topics;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class ContactTopicConfig {

	public static final String CONTACT_V1_CONTACT_NEW = "contact_v1_contact_new";
	public static final String CONTACT_V1_CONTACT_UPDATE = "contact_v1_contact_update";
	
	@Value("kafka.bootstrap-servers")
	private String kafkaBootstrapServers;
	
	@Bean
	public ProducerFactory<Object, Object> producerFactory() {
		Map<String, Object> producerProps = new HashMap<>();
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(producerProps);
	}

}