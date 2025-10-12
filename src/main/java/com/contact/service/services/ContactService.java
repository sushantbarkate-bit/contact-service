package com.contact.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.contact.service.entity.Contact;
import com.contact.service.repository.ContactRepository;
import java.util.List;
import java.util.UUID;

@Service
public class ContactService {
	private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public Contact saveContact(Contact contact) {
        return repository.save(contact);
    }

    public Page<Contact> getAllContacts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Contact getContactById(UUID id) {
        return repository.findById(id).orElse(null);
    }
    
    public Contact updateContact(UUID id, Contact contact) {
        Contact existingContact = repository.findById(id).orElse(null);
        if(existingContact != null) {
        	existingContact.setFirstName(contact.getFirstName());
        	existingContact.setLastName(contact.getLastName());;
        	existingContact.setEmail(contact.getEmail());
        	existingContact.setPhone(contact.getPhone());
        	existingContact.setMicr(contact.getMicr());
        	existingContact.setIfsc(contact.getIfsc());
        	existingContact.setAddress(contact.getAddress());
            existingContact.setCountry(contact.getCountry());
            existingContact.setState(contact.getState());
            existingContact.setZip(contact.getZip());
            existingContact.setCountry(contact.getCountry());
        }
        return existingContact;
    }
    
    public void deleteAll() {
    	repository.deleteAll();
    }

    public void deleteCotact(UUID id) {
        repository.deleteById(id);
    }
}
