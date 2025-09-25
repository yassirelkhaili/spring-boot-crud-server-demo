package de.szut.spring_boot_crud_service_demo.model;

import jakarta.persistence.Table;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import jakarta.persistence.GenerationType;

@Data
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "entry_generator", sequenceName = "entry_seq")
    private long id;

    private String comment;

    private String commenter;

    @Column(name = "date_of_entry", nullable = false, updatable = false)
    private LocalDate date = LocalDate.now();
}
