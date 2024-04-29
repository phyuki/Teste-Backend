package br.com.compass.challenge2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.compass.challenge2.entity.Genre;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
