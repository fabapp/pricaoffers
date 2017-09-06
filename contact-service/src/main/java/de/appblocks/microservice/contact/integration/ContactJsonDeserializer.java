package de.appblocks.microservice.contact.integration;

import org.springframework.kafka.support.serializer.JsonDeserializer;

import de.appblocks.microservice.contact.domain.Contact;

public class ContactJsonDeserializer extends JsonDeserializer<Contact> {
}