package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    // ---- CREATE ----
    public Book saveBook(Book book) {
        // Validate author exists
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new IllegalArgumentException("A valid author must be selected");
        }
        Author author = authorService.getAuthorByIdOrThrow(book.getAuthor().getId());
        book.setAuthor(author);

        // Check for duplicate ISBN
        if (book.getIsbn() != null && !book.getIsbn().isBlank()) {
            Optional<Book> existing = bookRepository.findByIsbn(book.getIsbn());
            if (existing.isPresent()) {
                throw new DataIntegrityViolationException("A book with ISBN " + book.getIsbn() + " already exists.");
            }
        }
        return bookRepository.save(book);
    }

    // ---- READ ----
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Book getBookByIdOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    @Transactional(readOnly = true)
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    /**
     * Custom INNER JOIN — returns combined book + author data.
     */
    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getAllBooksWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getBooksByGenreWithAuthors(String genre) {
        return bookRepository.findBooksWithAuthorsByGenre(genre);
    }

    // ---- UPDATE ----
    public Book updateBook(Long id, Book updatedBook) {
        Book existing = getBookByIdOrThrow(id);

        // Check ISBN uniqueness (excluding current book)
        if (updatedBook.getIsbn() != null && !updatedBook.getIsbn().isBlank()) {
            Optional<Book> isbnConflict = bookRepository.findByIsbn(updatedBook.getIsbn());
            if (isbnConflict.isPresent() && !isbnConflict.get().getId().equals(id)) {
                throw new DataIntegrityViolationException("ISBN " + updatedBook.getIsbn() + " is already in use.");
            }
        }

        existing.setTitle(updatedBook.getTitle());
        existing.setIsbn(updatedBook.getIsbn());
        existing.setGenre(updatedBook.getGenre());
        existing.setPublicationYear(updatedBook.getPublicationYear());
        existing.setPrice(updatedBook.getPrice());

        if (updatedBook.getAuthor() != null && updatedBook.getAuthor().getId() != null) {
            Author author = authorService.getAuthorByIdOrThrow(updatedBook.getAuthor().getId());
            existing.setAuthor(author);
        }

        return bookRepository.save(existing);
    }

    // ---- DELETE ----
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // ---- COUNT ----
    public long countBooks() {
        return bookRepository.count();
    }
}
