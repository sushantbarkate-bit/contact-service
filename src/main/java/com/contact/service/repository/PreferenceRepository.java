package com.contact.service.repository;

import com.contact.service.entity.Contact;
import com.contact.service.entity.ContactFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PreferenceRepository extends JpaRepository<ContactFilter,UUID> {
    Optional<ContactFilter> findByUserId(UUID userId);
}
