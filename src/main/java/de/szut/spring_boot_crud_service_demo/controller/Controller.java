package de.szut.spring_boot_crud_service_demo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping(value = "/SpringBootCrudService/welcome", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome!");
    }
}
