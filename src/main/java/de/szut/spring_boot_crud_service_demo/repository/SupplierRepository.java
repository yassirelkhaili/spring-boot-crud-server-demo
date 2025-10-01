package de.szut.spring_boot_crud_service_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.szut.spring_boot_crud_service_demo.model.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {}
