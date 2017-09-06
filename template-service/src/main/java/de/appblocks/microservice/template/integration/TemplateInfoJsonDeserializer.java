package de.appblocks.microservice.template.integration;

import org.springframework.kafka.support.serializer.JsonDeserializer;

import de.appblocks.microservice.template.domain.TemplateInfo;

public  class TemplateInfoJsonDeserializer extends JsonDeserializer<TemplateInfo> {
}