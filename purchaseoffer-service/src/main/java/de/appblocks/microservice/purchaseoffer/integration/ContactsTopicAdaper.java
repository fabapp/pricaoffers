package de.appblocks.microservice.purchaseoffer.integration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import de.appblocks.microservice.purchaseoffer.domain.Supplier;
import de.appblocks.microservice.purchaseoffer.domain.SupplierRepository;

@Component
public class ContactsTopicAdaper {

	
	private static final Logger logger = LoggerFactory.getLogger(ContactsTopicAdaper.class);

	
	@Autowired
	private SupplierRepository supplierRepository;

	@KafkaListener(topics = { "contact_v1_contact_new" })
	public void listen(ConsumerRecord<String, Supplier> cr) {
		Supplier supplier = cr.value();
		supplierRepository.save(supplier);
		System.out.println("ContactsTopicAdapter received Supplier: " + supplier);
		logger.debug("updated / created supplier record: {}", supplier);
	}

}
