package com.contact.service.entity;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class ContactSpecification {
    public static Specification<Contact> build(Contact filter) {
        return (Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction(); // WHERE 1=1

            if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("firstName")), "%" + filter.getFirstName().toLowerCase() + "%"));
            }
            if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("lastName")), "%" + filter.getLastName().toLowerCase() + "%"));
            }
            if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
            }
            if (filter.getPhone() != null && !filter.getPhone().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("phone"), "%" + filter.getPhone() + "%"));
            }
            if (filter.getMicr() != null && !filter.getMicr().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("micr"), filter.getMicr()));
            }
            if (filter.getIfsc() != null && !filter.getIfsc().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("ifsc")), "%" + filter.getIfsc().toLowerCase() + "%"));
            }
            if (filter.getAddress() != null && !filter.getAddress().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("address")), "%" + filter.getAddress().toLowerCase() + "%"));
            }
            if (filter.getCity() != null && !filter.getCity().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("city")), "%" + filter.getCity().toLowerCase() + "%"));
            }
            if (filter.getState() != null && !filter.getState().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("state")), "%" + filter.getState().toLowerCase() + "%"));
            }
            if (filter.getZip() != null && !filter.getZip().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("zip"), filter.getZip()));
            }
            if (filter.getCountry() != null && !filter.getCountry().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("country")), "%" + filter.getCountry().toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}
