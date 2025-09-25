package de.szut.spring_boot_crud_service_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.szut.spring_boot_crud_service_demo.model.Book;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findAllByOrderByIdDesc();
    public List<Book> findAllByDateBetweenOrderByDateDesc(LocalDate first, LocalDate second);
}
