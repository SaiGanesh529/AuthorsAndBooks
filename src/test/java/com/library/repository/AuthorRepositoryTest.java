package com.library.repository;

import com.library.entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        author1 = authorRepository.save(new Author("George Orwell",  "British",   1903, "Famous for 1984."));
        author2 = authorRepository.save(new Author("Jane Austen",    "British",   1775, "Famous for Pride & Prejudice."));
        authorRepository.save(new Author("Haruki Murakami", "Japanese", 1949, "Japanese author."));
    }

    @Test
    void testSaveAndFindById() {
        Optional<Author> found = authorRepository.findById(author1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("George Orwell");
    }

    @Test
    void testFindAll() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(3);
    }

    @Test
    void testFindByNationality() {
        List<Author> british = authorRepository.findByNationality("British");
        assertThat(british).hasSize(2);
    }

    @Test
    void testFindByNameIgnoreCase() {
        Optional<Author> found = authorRepository.findByNameIgnoreCase("george orwell");
        assertThat(found).isPresent();
        assertThat(found.get().getBirthYear()).isEqualTo(1903);
    }

    @Test
    void testFindByNameContaining() {
        List<Author> results = authorRepository.findByNameContainingIgnoreCase("aus");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Jane Austen");
    }

    @Test
    void testUpdateAuthor() {
        author1.setNationality("English");
        Author updated = authorRepository.save(author1);
        assertThat(updated.getNationality()).isEqualTo("English");
    }

    @Test
    void testDeleteAuthor() {
        authorRepository.deleteById(author2.getId());
        assertThat(authorRepository.findById(author2.getId())).isEmpty();
        assertThat(authorRepository.count()).isEqualTo(2);
    }

    @Test
    void testCount() {
        assertThat(authorRepository.count()).isEqualTo(3);
    }
}
