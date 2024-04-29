package br.com.compass.challenge2.controller;
import br.com.compass.challenge2.entity.Book;
import br.com.compass.challenge2.entity.Genre;
import br.com.compass.challenge2.service.BookService;
import br.com.compass.challenge2.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Genre genre = genreService.findById(book.getGenre().getId());

        if (genre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book createdBook = new Book(null, book.getName(),
                book.getPages(), book.getStock(), genre);
        createdBook = bookService.save(createdBook);

        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book book) {
        Book existingBook = bookService.findById(id);
        Genre genre = genreService.findById(book.getGenre().getId());
        if (existingBook != null) {

            if (!existingBook.getGenre().getId().equals(genre.getId())) {
                existingBook.setGenre(genre);
            }

            if (genre == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            existingBook.setName(book.getName());
            existingBook.setPages(book.getPages());
            existingBook.setStock(book.getStock());

            existingBook = bookService.update(existingBook);

            return new ResponseEntity<>(existingBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book existingAssessment =  bookService.findById(id);
        if (existingAssessment != null) {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllAssessments() {
        List<Book> books = bookService.findAll();
        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
