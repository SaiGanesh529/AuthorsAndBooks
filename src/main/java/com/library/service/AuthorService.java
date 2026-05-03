package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // ---- CREATE ----
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // ---- READ ----
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Author getAuthorByIdOrThrow(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Author> getAuthorsByNationality(String nationality) {
        return authorRepository.findByNationality(nationality);
    }

    @Transactional(readOnly = true)
    public List<Author> searchAuthors(String keyword) {
        return authorRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Transactional(readOnly = true)
    public List<Author> getAuthorsWithBooks() {
        return authorRepository.findAuthorsWithBooks();
    }

    // ---- UPDATE ----
    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existing = getAuthorByIdOrThrow(id);
        existing.setName(updatedAuthor.getName());
        existing.setNationality(updatedAuthor.getNationality());
        existing.setBirthYear(updatedAuthor.getBirthYear());
        existing.setBiography(updatedAuthor.getBiography());
        return authorRepository.save(existing);
    }

    // ---- DELETE ----
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    // ---- COUNT ----
    public long countAuthors() {
        return authorRepository.count();
    }
}
