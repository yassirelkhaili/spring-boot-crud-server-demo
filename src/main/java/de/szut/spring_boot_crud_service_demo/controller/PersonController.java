package de.szut.spring_boot_crud_service_demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.szut.spring_boot_crud_service_demo.model.Person;
import de.szut.spring_boot_crud_service_demo.repository.PersonRepository;

import java.net.URI;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/SpringBootCrudService/person/welcome", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome!");
    }

    @RequestMapping(value = "/SpringBootCrudService/person", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Person> createPerson(@RequestBody Person newPerson) {
        try {
            Person savedPerson = this.personRepository.save(newPerson);
            return ResponseEntity.created(URI.create("/SpringBootCrudService/person/" + savedPerson.getId()))
                    .body(savedPerson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/person/{id}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Person> getPersonById(@PathVariable long id) {
        try {
            Optional<Person> person = this.personRepository.findById(id);
            if (person.isPresent()) {
                return ResponseEntity.ok(person.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/person", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<Person>> getAllPersons() {
        try {
            List<Person> people = this.personRepository.findAll();
            if (people.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(people);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/person/{id}", method = RequestMethod.PATCH, consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Person> updatePerson(@PathVariable long id, @RequestBody Person newPerson) {
        try {
            Optional<Person> person = this.personRepository.findById(id);
            if (person.isPresent()) {
                newPerson.setId(id);
                Person savedPerson = this.personRepository.save(newPerson);
                return ResponseEntity.ok()
                        .header("Location", "/SpringBootCrudService/person/" + savedPerson.getId())
                        .body(savedPerson);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/person/{id}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Person> deletePersonById(@PathVariable long id) {
        try {
            Optional<Person> person = this.personRepository.findById(id);
            if (person.isPresent()) {
                this.personRepository.delete(person.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
