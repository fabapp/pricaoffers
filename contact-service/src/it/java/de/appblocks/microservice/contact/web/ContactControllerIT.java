package de.appblocks.microservice.contact.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.appblocks.microservice.contact.domain.Contact;
import de.appblocks.microservice.contact.service.ContactService;
import de.appblocks.microservice.contact.web.ContactController;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ContactControllerIT {

	@MockBean
	ContactService contactService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper om;
	
	@Test
	public void getContactById() throws Exception {
		int id = 1;
		Contact contact = new Contact(id);
		Mockito.when(contactService.getContact(id)).thenReturn(contact);
		
		String expectedContent = om.writeValueAsString(contact);
		mockMvc.perform(get("/v1/contacts/" + id).contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(content().string(expectedContent));
		
		Mockito.verify(contactService).getContact(id);
	}
	
}
