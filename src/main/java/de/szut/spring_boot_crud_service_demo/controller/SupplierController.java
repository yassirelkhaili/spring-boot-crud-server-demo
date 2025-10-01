package de.szut.spring_boot_crud_service_demo.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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

import de.szut.spring_boot_crud_service_demo.model.Supplier;
import de.szut.spring_boot_crud_service_demo.repository.SupplierRepository;

public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;

    @RequestMapping(value = "/SpringBootCrudService/book", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier newSupplier) {
        try {
            Supplier savedSupplier = this.supplierRepository.save(newSupplier);
            return ResponseEntity.created(URI.create("/SpringBootCrudService/book/" + savedSupplier.getId()))
                    .body(savedSupplier);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/book/{id}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Supplier> getSupplierById(@PathVariable long id) {
        try {
            Optional<Supplier> book = this.supplierRepository.findById(id);
            if (book.isPresent()) {
                return ResponseEntity.ok(book.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        try {
            List<Supplier> supplier = new ArrayList<Supplier>();
                supplier = this.supplierRepository.findAll();
            if (supplier.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(supplier);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/book/{id}", method = RequestMethod.PATCH, consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Supplier> updateSupplier(@PathVariable long id, @RequestBody Supplier newSupplier) {
        try {
            Optional<Supplier> book = this.supplierRepository.findById(id);
            if (book.isPresent()) {
                newSupplier.setId(id);
                Supplier savedSupplier = this.supplierRepository.save(newSupplier);
                return ResponseEntity.ok()
                        .header("Location", "/SpringBootCrudService/book/" + savedSupplier.getId())
                        .body(savedSupplier);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/book/{id}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Supplier> deleteSupplierById(@PathVariable long id) {
        try {
            Optional<Supplier> book = this.supplierRepository.findById(id);
            if (book.isPresent()) {
                this.supplierRepository.delete(book.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
