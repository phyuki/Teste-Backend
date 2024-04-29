package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Genre;
import br.com.compass.challenge2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import br.com.compass.challenge2.entity.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService implements CrudService<Book> {

    private BookRepository bookRepository;
    private GenreService genreService;
    @Autowired
    public BookService(BookRepository repository, GenreService studentService) {
        this.bookRepository = repository;
        this.genreService = studentService;
    }

    @Override
    public Book save(Book assessment) {
        if (assessment.getId() == null) {
            return bookRepository.save(assessment);
        } else {
            throw new IllegalArgumentException("The \"id\" attribute is not allowed when creating a new book.");
        }
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> optionalAssessment = bookRepository.findById(id);
        if (optionalAssessment.isPresent()) {
            return optionalAssessment.get();
        } else {
            throw new EntityNotFoundException("Book does not exist with id: " + id);
        }
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book update(Book assessment) {
        if (bookRepository.existsById(assessment.getId())) {
            return bookRepository.save(assessment);
        } else {
            throw new EntityNotFoundException("Book does not exist with id: " + assessment.getId());
        }
    }

    @Override
    @Transactional
    public Book deleteById(Long id) {
        Book book;
        try {
            book = bookRepository.findById(id).get();
            Genre genre = book.getGenre();

            if (genre != null) {
                genre.getBooks().remove(book);
                book.setGenre(null);
            }

            bookRepository.delete(book);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Book does not exist with id: " + id);
        }

        return book;
    }
    public List<Book> getBooksByGenreId(Long genreId) {
        Genre genre = genreService.findById(genreId);
        if (genre == null) {
            throw new EntityNotFoundException("Genre not found with id: " + genreId);
        }
        return bookRepository.findByGenre(genre);
    }
}
