package com.contact.service.services;

import com.contact.service.entity.Contact;
import com.contact.service.entity.ContactFilter;
import com.contact.service.repository.ContactRepository;
import com.contact.service.repository.PreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PreferenceService {
    private final PreferenceRepository repository;

    public PreferenceService(PreferenceRepository repository) {
        this.repository = repository;
    }

    public Optional<ContactFilter> getFilter(UUID id) {
        return repository.findByUserId(id);
    }

    public ContactFilter saveFilter(ContactFilter filter) {
        Optional<ContactFilter> contactFilter =  repository.findByUserId(filter.getUserId());
        if(!contactFilter.isEmpty()) {
            contactFilter.get().setFilter(filter.getFilter());
            contactFilter.get().setUserId(filter.getUserId());
            return repository.save(contactFilter.get());
        } else {
            return repository.save(filter);
        }
    }
}
