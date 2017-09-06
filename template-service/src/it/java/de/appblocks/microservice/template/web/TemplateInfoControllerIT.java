package de.appblocks.microservice.template.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.appblocks.microservice.template.domain.TemplateInfo;
import de.appblocks.microservice.template.service.TemplateService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TemplateInfoControllerIT {

	@MockBean
	TemplateService templateService;
	
	@Autowired
	TemplateController templateController;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper om;
	
	@Test
	public void getTemplateInfoById() throws Exception {
		int id = 1;
		TemplateInfo templateInfo = new TemplateInfo(id);
		Mockito.when(templateService.getTemplate(id)).thenReturn(templateInfo);
		
		String expectedContent = om.writeValueAsString(templateInfo);
		mockMvc.perform(get("/templates/" + id)).andExpect(content().string(expectedContent));
		
		Mockito.verify(templateService).getTemplate(id);
	}
	
}
