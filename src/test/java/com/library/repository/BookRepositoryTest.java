package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
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
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;
    private Book book1;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author = authorRepository.save(new Author("George Orwell", "British", 1903, "English novelist."));

        book1 = bookRepository.save(new Book("1984", "978-0451524935", "Dystopian", 1949, 12.99, author));
        bookRepository.save(new Book("Animal Farm", "978-0451526342", "Satire", 1945, 9.99, author));
    }

    @Test
    void testSaveAndFindBook() {
        Optional<Book> found = bookRepository.findById(book1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("1984");
    }

    @Test
    void testFindAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2);
    }

    @Test
    void testFindByAuthorId() {
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        assertThat(books).hasSize(2);
    }

    @Test
    void testFindByIsbn() {
        Optional<Book> found = bookRepository.findByIsbn("978-0451524935");
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("1984");
    }

    @Test
    void testFindByTitleContaining() {
        List<Book> results = bookRepository.findByTitleContainingIgnoreCase("animal");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Animal Farm");
    }

    @Test
    void testFindByGenre() {
        List<Book> dystopian = bookRepository.findByGenreIgnoreCase("Dystopian");
        assertThat(dystopian).hasSize(1);
        assertThat(dystopian.get(0).getTitle()).isEqualTo("1984");
    }

    /**
     * Tests the custom INNER JOIN JPQL query.
     * Verifies that combined book + author data is returned correctly.
     */
    @Test
    void testFindAllBooksWithAuthors_InnerJoin() {
        List<BookAuthorDTO> result = bookRepository.findAllBooksWithAuthors();
        assertThat(result).hasSize(2);

        BookAuthorDTO dto = result.get(0);
        assertThat(dto.getAuthorName()).isEqualTo("George Orwell");
        assertThat(dto.getNationality()).isEqualTo("British");
        assertThat(dto.getBookTitle()).isNotNull();
    }

    @Test
    void testUpdateBook() {
        book1.setPrice(15.99);
        Book updated = bookRepository.save(book1);
        assertThat(updated.getPrice()).isEqualTo(15.99);
    }

    @Test
    void testDeleteBook() {
        bookRepository.deleteById(book1.getId());
        assertThat(bookRepository.findById(book1.getId())).isEmpty();
        assertThat(bookRepository.count()).isEqualTo(1);
    }
}
