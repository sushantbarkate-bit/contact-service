package com.contact.service.services;

import com.contact.service.entity.ContactSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.contact.service.entity.Contact;
import com.contact.service.repository.ContactRepository;

import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public List<Contact> allContacts() {
        return repository.findAll();
    }

    public Contact getContactById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Optional<Contact> getNextContact(UUID id) {
        return repository.findFirstByIdGreaterThanOrderByIdAsc(id);
    }

    public Optional<Contact> getPreviousContact(UUID id) {
        return repository.findTopByIdLessThanOrderByIdDesc(id);
    }

    public ResponseEntity<?> searchContacts(Contact filter, Pageable pageable) {
        Specification<Contact> spec = ContactSpecification.build(filter);
        Page<Contact> pageableContact = repository.findAll(spec, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("total-count", String.valueOf(pageableContact.getTotalElements()));
        headers.add("total-pages", String.valueOf(pageableContact.getTotalPages()));
        return ResponseEntity.ok().headers(headers).body(pageableContact.getContent());
    }


    public Contact updateContact(UUID id, Contact contact) {
        Contact existingContact = repository.findById(id).orElse(null);
        if (existingContact != null) {
            existingContact.setFirstName(contact.getFirstName());
            existingContact.setLastName(contact.getLastName());
            ;
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
