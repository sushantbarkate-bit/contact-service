package com.contact.service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.contact.service.entity.Contact;
import com.contact.service.services.ContactService;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/contacts/service")
@CrossOrigin(origins = "*")
public class ContactController {
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Contact updateContact(@PathVariable UUID id, @RequestBody Contact contact) {
        System.out.println("t4rdddfadf " + contact);
        return service.updateContact(id, contact);
    }

    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {
        return service.saveContact(contact);
    }

    @GetMapping
    public List<Contact> getContacts(Pageable pageable) {
        return service.getAllContacts(pageable).getContent();
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable UUID id) {
        return service.getContactById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable UUID id) {
        service.deleteCotact(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllContact() {
        service.deleteAll();
        return ResponseEntity.notFound().build();
    }
}
