package de.appblocks.microservice.template.infra.messaging;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import de.appblocks.microservice.template.domain.TemplateInfo;
import de.appblocks.microservice.template.integration.TemplateInfoJsonDeserializer;
import de.appblocks.microservice.template.integration.TemplateTopicAdapter;
import de.appblocks.microservice.template.service.TemplateService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class KafkaAdapterIT {

	private static final String TEMPLATE_INFO_DATA_RESPONSE = "template_info_data_response";

	private static final String TEMPLATE_INFO_DATA_REQUEST = "template_info_data_request";

	@Autowired
	private KafkaTemplate<String, Integer> template;

	private final CountDownLatch latch = new CountDownLatch(1);

	@MockBean
	private TemplateService templateService;

	@Autowired
	TemplateTopicAdapter templateTopicAdapter;

	@ClassRule
	public static KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(2, false, TEMPLATE_INFO_DATA_REQUEST,
			TEMPLATE_INFO_DATA_RESPONSE);

	static TemplateInfo templateInfoReceived;

	@TestConfiguration
	public static class CatalogItemDataRequestListenerTestConfiguration {
		@Bean
		public ProducerFactory<Object, Object> producerFactory() {
			Map<String, Object> producerProps = KafkaTestUtils.producerProps(kafkaEmbedded);
			producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
					producerProps.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
			producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
			return new DefaultKafkaProducerFactory<>(producerProps);
		}

		@Bean
		ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory() {
			Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("service-template", "true", kafkaEmbedded);
			consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TemplateInfoJsonDeserializer.class);
			DefaultKafkaConsumerFactory<Object, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(
					consumerProps);
			ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
			factory.setConsumerFactory(consumerFactory);
			return factory;
		}
	}

	@Test
	public void run() throws Exception {
		Integer id = 1;
		TemplateInfo templateInfo = new TemplateInfo(id);
		templateInfo.setDescription("description");
		Mockito.when(templateService.getTemplate(id)).thenReturn(templateInfo);
		// FIXME: Thread.sleep() ! Need to wait for kafka ?!
		Thread.sleep(2000);
		ListenableFuture<SendResult<String, Integer>> future = template.send(TEMPLATE_INFO_DATA_REQUEST, id);
		future.get();
		latch.await(10, TimeUnit.SECONDS);
		assertThat(templateInfo).isEqualTo(templateInfoReceived);
	}

	@KafkaListener(topics = TEMPLATE_INFO_DATA_RESPONSE)
	public void sendTemplateInfoDataRequest(ConsumerRecord<Integer, TemplateInfo> cr) throws Exception {
		templateInfoReceived = cr.value();
		latch.countDown();
	}
}
