package com.contact.service.controller;

import com.contact.service.entity.Contact;
import com.contact.service.entity.ContactFilter;
import com.contact.service.services.ContactService;
import com.contact.service.services.PreferenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/preference")
@CrossOrigin(origins = "*")
public class PreferenceController {
    private static final Logger logger = LoggerFactory.getLogger(PreferenceController.class);
    private final PreferenceService service;

    public PreferenceController(PreferenceService service) {
        this.service = service;
    }

    @PostMapping("/save/filter/{id}")
    public ContactFilter savefilters(@RequestBody Contact filter, @PathVariable UUID id) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonString = mapper.writeValueAsString(filter);
        final ContactFilter contactFilter = ContactFilter.builder()
                .filter(jsonString)
                .userId(id)
                .build();
        return service.saveFilter(contactFilter);
    }

    @GetMapping("/filter/{id}")
    public Optional<ContactFilter> getfilters(@PathVariable UUID id) {
        return service.getFilter(id);
    }
}
