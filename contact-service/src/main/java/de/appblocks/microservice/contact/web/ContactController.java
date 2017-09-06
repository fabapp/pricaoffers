package de.appblocks.microservice.contact.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.appblocks.microservice.contact.domain.Contact;
import de.appblocks.microservice.contact.service.ContactService;

@RestController
@RequestMapping(path="/v1", 
				consumes={"application/json;charset=UTF-8"}, 
				produces={"application/json;charset=UTF-8"})
public class ContactController {

	@Autowired
	private ContactService contactService;

	@GetMapping("/contacts/{id}")
	public Contact getContact(@PathVariable("id") Integer id) {
		return contactService.getContact(id);
	}
	
	@GetMapping(path = "/contacts/", params = { "page", "size" })
	public Page<Contact> getContact(Pageable pageable) {
		return contactService.getContacts(pageable);
	}

	@PostMapping("/contacts/")
	public Contact saveContact(@RequestBody Contact contact) {
		return contactService.saveContact(contact);
	}
}
