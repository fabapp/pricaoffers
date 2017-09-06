package de.appblocks.microservice.template.integration;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import de.appblocks.microservice.template.domain.TemplateInfo;
import de.appblocks.microservice.template.service.TemplateService;

@Component
@Aspect
public class TemplateTopicAdapter {
	
	@Autowired
    private KafkaTemplate<String, TemplateInfo> templateInfo;
	
	@Autowired
	private TemplateService templateService;
	
	@AfterReturning(
	        pointcut="execution(* de.appblocks.microservice.template.web.TemplateController.saveTemplate(de.appblocks.microservice.template.domain.TemplateInfo))",
	        returning="info")
	public void listen(TemplateInfo info) throws Exception {
		this.templateInfo.send(TemplateTopic.TEMPLATE_V1_TEMPLATE_NEW, info);
	}
	
	//	@KafkaListener(topics = {TemplateTopic.TEMPLATE_V1_TEMPLATE_NEW})
	
}
