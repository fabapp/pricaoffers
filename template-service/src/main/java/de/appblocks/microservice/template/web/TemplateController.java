package de.appblocks.microservice.template.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.appblocks.microservice.template.domain.TemplateInfo;
import de.appblocks.microservice.template.service.TemplateService;

@RestController
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@GetMapping("/templates/{templateId}")
	public TemplateInfo getTemplate(@PathVariable("templateId") Integer templateId) {
		return templateService.getTemplate(templateId);
	}

	@GetMapping(path = "/templates/", params = { "page", "size" })
	public Page<TemplateInfo> getTemplate(Pageable pageable) {
		return templateService.getTemplates(pageable);
	}

	@PostMapping("/templates/")
	public TemplateInfo saveTemplate(@RequestBody TemplateInfo templateInfo) {
		return templateService.saveTemplate(templateInfo);
	}
}
