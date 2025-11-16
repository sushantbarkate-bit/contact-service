package com.contact.service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.contact.service.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID>, JpaSpecificationExecutor<Contact> {

    Optional<Contact> findFirstByIdGreaterThanOrderByIdAsc(UUID currentId);

    Optional<Contact> findTopByIdLessThanOrderByIdDesc(UUID currentId);
}
