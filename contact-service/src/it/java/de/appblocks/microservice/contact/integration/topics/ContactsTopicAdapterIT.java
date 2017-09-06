package de.appblocks.microservice.contact.integration.topics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.appblocks.microservice.contact.domain.Contact;
import de.appblocks.microservice.contact.integration.ContactJsonDeserializer;
import de.appblocks.microservice.contact.integration.ContactTopic;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContactsTopicAdapterIT {

	@ClassRule
	public static KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(2, false, ContactTopic.CONTACT_V1_CONTACT_NEW);

	@Autowired
	private ObjectMapper om;

	CountDownLatch latch = new CountDownLatch(1);

	@TestConfiguration
	public static class CatalogItemDataRequestListenerTestConfiguration {
		@Bean
		public ProducerFactory<Object, Object> producerFactory() {
			Map<String, Object> producerProps = KafkaTestUtils.producerProps(kafkaEmbedded);
			producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
			return new DefaultKafkaProducerFactory<>(producerProps);
		}

		@Bean
		ConcurrentKafkaListenerContainerFactory<Object, Contact> kafkaListenerContainerFactory() {
			Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("service-template", "true", kafkaEmbedded);
			consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ContactJsonDeserializer.class);
			DefaultKafkaConsumerFactory<Object, Contact> consumerFactory = new DefaultKafkaConsumerFactory<>(
					consumerProps);
			ConcurrentKafkaListenerContainerFactory<Object, Contact> factory = new ConcurrentKafkaListenerContainerFactory<>();
			factory.setConsumerFactory(consumerFactory);
			return factory;
		}
	}

	@Autowired
	private TestRestTemplate restTemplate;

	private static Contact contactReceived;

	@Test
	public void onSave_contactShouldBeSentToCTopic() throws Exception {
		Contact contact = new Contact(1);
		contact.setName("John Doe");
		contact.setEmail("john.doe@mymail.com");
		String contactJson = om.writeValueAsString(contact);
		ResponseEntity<Contact> contactReturned = restTemplate.postForEntity("/contacts/", contact, Contact.class);
		latch.await();
		assertThat(contactReceived).isEqualTo(contact);
		assertThat(contactReceived).isEqualTo(contactReturned.getBody());
	}

	@KafkaListener(topics = ContactTopic.CONTACT_V1_CONTACT_NEW)
	public void sendTemplateInfoDataRequest(ConsumerRecord<Object, Contact> cr) throws Exception {
		contactReceived = cr.value();
		latch.countDown();
	}
}
