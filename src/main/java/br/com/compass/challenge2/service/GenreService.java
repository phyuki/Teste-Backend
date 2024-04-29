package br.com.compass.challenge2.service;

import java.util.List;
import java.util.NoSuchElementException;

import br.com.compass.challenge2.entity.Book;
import br.com.compass.challenge2.repository.BookRepository;
import br.com.compass.challenge2.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.compass.challenge2.entity.Genre;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class GenreService implements CrudService<Genre> {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreService(GenreRepository studentRepository, BookRepository bookRepository) {
        this.genreRepository = studentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Genre save(Genre student) {
        if (student.getId() == null)
            return genreRepository.save(student);

        throw new IllegalArgumentException("The \"id\" attribute is not allowed when creating a new literary genre.");
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(Long id) {
        Genre student;

        if (genreRepository.findById(id).isPresent())
            student = genreRepository.findById(id).get();
        else
            throw new EntityNotFoundException("Literary genre does not exist with id: " + id);

        return student;
    }

    @Override
    public Genre update(Genre student) {

        if (genreRepository.existsById(student.getId()))
            return genreRepository.save(student);

        throw new EntityNotFoundException("Literary genre does not exist with id: " + student.getId());
    }

    @Transactional
    @Override
    public Genre deleteById(Long id) {
        Genre genre;

        try {
            genre = genreRepository.findById(id).get();
            genreRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Literary genre does not exist with id: " + id);
        }

        return genre;
    }

}
