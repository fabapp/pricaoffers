package de.appblocks.microservice.contact.integration.topics;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import de.appblocks.microservice.contact.domain.Contact;

@Component
@Aspect
public class ContactTopicAdapter {
	
	public static final String CONTACT_V1_CONTACT_NEW = "contact_v1_contact_new";
	public static final String CONTACT_V1_CONTACT_UPDATE = "contact_v1_contact_update";
	
	private static final Logger log = LoggerFactory.getLogger(ContactTopicAdapter.class);
	
	@Configuration
	public static class ContactsTopicAdapterConfig {
		
	}
	
	@Autowired
    private KafkaTemplate<String, Contact> template;
	
	// TODO: Use kafka connector on database level
	@AfterReturning(
	        pointcut="execution(* de.appblocks.microservice.contact.web.ContactController.saveContact(de.appblocks.microservice.contact.domain.Contact))",
	        returning="contact")
	public void handleSaveContact(Contact contact) throws Exception {
		ListenableFuture<SendResult<String,Contact>> future = template.send(ContactTopicConfig.CONTACT_V1_CONTACT_NEW, contact.getId().toString(), contact); // called on save and update
		future.addCallback(result -> {
			log.debug("sent {} to {}.", contact, ContactTopicConfig.CONTACT_V1_CONTACT_NEW);	
		}, failure -> {
			log.error("Error sending contact {} to {} ", failure, contact, ContactTopicConfig.CONTACT_V1_CONTACT_NEW);
		});
	}
}
