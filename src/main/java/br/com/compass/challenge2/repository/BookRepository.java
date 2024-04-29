package br.com.compass.challenge2.repository;
import br.com.compass.challenge2.entity.Book;
import br.com.compass.challenge2.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenre(Genre genre);
}
