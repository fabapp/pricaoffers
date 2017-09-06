package de.appblocks.microservice.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.appblocks.microservice.contact.domain.Contact;
import de.appblocks.microservice.contact.domain.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;

	public Contact getContact(Integer id) {
		return contactRepository.findOne(id);
	}

	public Page<Contact> getContacts(Pageable pageable) {
		return contactRepository.findAll(pageable);
	}

	public Contact saveContact(Contact contact) {
		return contactRepository.save(contact);
	}

	
}
