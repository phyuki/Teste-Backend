package br.com.compass.challenge2.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.NoSuchElementException;
import br.com.compass.challenge2.entity.Book;
import br.com.compass.challenge2.entity.Genre;
import br.com.compass.challenge2.repository.BookRepository;
import br.com.compass.challenge2.repository.GenreRepository;
import br.com.compass.challenge2.service.BookService;
import br.com.compass.challenge2.service.GenreService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;
    private final BookService bookService;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreController(GenreService studentService, BookService bookService,
                           GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreService = studentService;
        this.bookService = bookService;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<Genre> createStudent(@Valid @RequestBody Genre genre) {

        Genre createdGenre = new Genre(null, genre.getName(), genre.getDesc(), null);
        genreService.save(createdGenre);

        return new ResponseEntity<>(createdGenre, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Genre>> findAllStudents() {
        List<Genre> students = genreService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> findStudentById(@PathVariable Long id) {
        Genre genre = genreService.findById(id);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateStudent(@Valid @PathVariable Long id,
                                                 @RequestBody Genre genre) {

        Genre student = genreService.findById(id);
        Genre newStudent = new Genre(id, genre.getName(),
                genre.getDesc(), student.getBooks());
        Genre updatedStudent = genreService.update(newStudent);

        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Genre> deleteGenre(@PathVariable Long id) {
        Genre genre = genreService.findById(id);
        Genre deletedGenre = genreService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
