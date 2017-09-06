package de.appblocks.microservice.template.service;

import org.springframework.data.jpa.repository.JpaRepository;

import de.appblocks.microservice.template.domain.TemplateInfo;

public interface TemplateInfoRepository extends JpaRepository<TemplateInfo, Integer> {

}
