package de.appblocks.microservice.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.appblocks.microservice.template.domain.TemplateInfo;

@Service
public class TemplateService {

	@Autowired
	private TemplateInfoRepository templateInfoRepository;

	public TemplateInfo getTemplate(Integer id) {
		return templateInfoRepository.findOne(id);
	}

	public Page<TemplateInfo> getTemplates(Pageable pageable) {
		return templateInfoRepository.findAll(pageable);
	}

	public TemplateInfo saveTemplate(TemplateInfo templateInfo) {
		return templateInfoRepository.save(templateInfo);
	}

	
}
