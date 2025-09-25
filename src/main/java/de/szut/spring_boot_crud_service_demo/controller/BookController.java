package de.szut.spring_boot_crud_service_demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import de.szut.spring_boot_crud_service_demo.model.Book;
import de.szut.spring_boot_crud_service_demo.repository.BookRepository;

import java.util.ArrayList;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.time.LocalDate;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = "/SpringBootCrudService/book", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Book> createBook(@RequestBody Book newBook) {
        try {
            Book savedBook = this.bookRepository.save(newBook);
            return ResponseEntity.created(URI.create("/SpringBootCrudService/book/" + savedBook.getId()))
                    .body(savedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/book/{id}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        try {
            Optional<Book> book = this.bookRepository.findById(id);
            if (book.isPresent()) {
                return ResponseEntity.ok(book.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) Integer year) {
        try {
            List<Book> books = new ArrayList<Book>();
            if (Objects.isNull(year)) {
                books = this.bookRepository.findAllByOrderByIdDesc();
            } else {
                books = this.bookRepository.findAllByDateBetweenOrderByDateDesc(LocalDate.of(year, 1, 1),
                        LocalDate.of(year, 12, 31));
            }
            if (books.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/book/{id}", method = RequestMethod.PATCH, consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book newBook) {
        try {
            Optional<Book> book = this.bookRepository.findById(id);
            if (book.isPresent()) {
                newBook.setId(id);
                Book savedBook = this.bookRepository.save(newBook);
                return ResponseEntity.ok()
                        .header("Location", "/SpringBootCrudService/book/" + savedBook.getId())
                        .body(savedBook);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/SpringBootCrudService/book/{id}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Book> deleteBookById(@PathVariable long id) {
        try {
            Optional<Book> book = this.bookRepository.findById(id);
            if (book.isPresent()) {
                this.bookRepository.delete(book.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
