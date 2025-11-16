package com.contact.service.controller;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.contact.service.entity.Contact;
import com.contact.service.services.ContactService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/contacts/service")
@CrossOrigin(origins = "*")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Contact updateContact(@PathVariable UUID id, @RequestBody Contact contact) {
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

    @GetMapping("next/{id}")
    public ResponseEntity<?> getNextContact(@PathVariable UUID id) {
        final Optional<Contact> contact = service.getNextContact(id);
        if (contact.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(contact);
        }
    }

    @GetMapping("previous/{id}")
    public ResponseEntity<?> getPreviousContact(@PathVariable UUID id) {
        final Optional<Contact> contact = service.getPreviousContact(id);
        if (contact.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(contact);
        }
    }

    @DeleteMapping("/delete-all")
    public void deleteALlContact() {
        service.deleteAll();
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

    @PostMapping("/filter")
    public ResponseEntity<?> filterContacts(
            @RequestBody Contact filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName,asc") String[] sort) {

        Sort.Direction direction = sort.length > 1 && sort[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        return service.searchContacts(filter, pageable);
    }

    @GetMapping("/export")
    public void exportUsersToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"customer.csv\"");

        List<Contact> contacts = service.allContacts();

        PrintWriter writer = response.getWriter();
        writer.println("ID,Name,Email,Phone,City,MICR,IFSC,Address,State,Zip-code,Country");
        for (Contact contact : contacts) {
            writer.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                    contact.getId(),
                    escapeCsv(contact.getFirstName()+ " " + contact.getLastName()),
                    escapeCsv(contact.getEmail()),
                    escapeCsv(contact.getPhone()),
                    escapeCsv(contact.getCity()),
                    escapeCsv(contact.getMicr()),
                    escapeCsv(contact.getIfsc()),
                    escapeCsv(contact.getAddress()),
                    escapeCsv(contact.getState()),
                    escapeCsv(contact.getZip()),
                    escapeCsv(contact.getCountry())

            ));
        }
        writer.flush();
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }
}
