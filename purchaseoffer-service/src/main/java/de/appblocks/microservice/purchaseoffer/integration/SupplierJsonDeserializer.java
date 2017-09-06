package de.appblocks.microservice.purchaseoffer.integration;

import org.springframework.kafka.support.serializer.JsonDeserializer;

import de.appblocks.microservice.purchaseoffer.domain.Supplier;

public class SupplierJsonDeserializer extends JsonDeserializer<Supplier> {
}