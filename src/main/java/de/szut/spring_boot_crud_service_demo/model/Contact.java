package de.szut.spring_boot_crud_service_demo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Street is mandatory")
    @Size(max = 50, message = "Street must not exceed 50 charracters")
    private String street;

    @Column(name = "zip")
    @NotBlank(message = "Postcode is mandatory")
    @Size(min = 5, max = 5, message = "Postcode must have 5 charracters")
    private String postcode;

    @NotBlank(message = "City is mandatory")
    @Size(max = 50, message = "City must not exceed 50 charracters")
    private String city;

    private String phone;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Supplier supplier;
}
