package com.contact.service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact.service.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {


}
