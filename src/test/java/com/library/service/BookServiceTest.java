package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    private Author mockAuthor;
    private Book mockBook;

    @BeforeEach
    void setUp() {
        mockAuthor = new Author("George Orwell", "British", 1903, "Novelist.");
        mockAuthor.setId(1L);

        mockBook = new Book("1984", "978-0451524935", "Dystopian", 1949, 12.99, mockAuthor);
        mockBook.setId(1L);
    }

    // ---- SAVE ----
    @Test
    void testSaveBook_Success() {
        when(authorService.getAuthorByIdOrThrow(1L)).thenReturn(mockAuthor);
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(mockBook);

        Book saved = bookService.saveBook(mockBook);

        assertThat(saved.getTitle()).isEqualTo("1984");
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testSaveBook_DuplicateIsbn_ThrowsException() {
        when(authorService.getAuthorByIdOrThrow(1L)).thenReturn(mockAuthor);
        when(bookRepository.findByIsbn("978-0451524935")).thenReturn(Optional.of(mockBook));

        assertThatThrownBy(() -> bookService.saveBook(mockBook))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void testSaveBook_NoAuthor_ThrowsException() {
        Book bookWithNoAuthor = new Book("Orphan Book", "111", "Fiction", 2000, 9.99, null);

        assertThatThrownBy(() -> bookService.saveBook(bookWithNoAuthor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("valid author");
    }

    // ---- GET ALL ----
    @Test
    void testGetAllBooks() {
        Book book2 = new Book("Animal Farm", "978-0451526342", "Satire", 1945, 9.99, mockAuthor);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(mockBook, book2));

        List<Book> result = bookService.getAllBooks();

        assertThat(result).hasSize(2);
    }

    // ---- GET BY ID ----
    @Test
    void testGetBookByIdOrThrow_NotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookByIdOrThrow(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Book not found with id: 99");
    }

    // ---- INNER JOIN QUERY ----
    @Test
    void testGetAllBooksWithAuthors() {
        BookAuthorDTO dto = new BookAuthorDTO(
            1L, "1984", "978-0451524935", "Dystopian", 1949, 12.99,
            1L, "George Orwell", "British"
        );
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(List.of(dto));

        List<BookAuthorDTO> result = bookService.getAllBooksWithAuthors();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthorName()).isEqualTo("George Orwell");
        assertThat(result.get(0).getBookTitle()).isEqualTo("1984");
    }

    // ---- UPDATE ----
    @Test
    void testUpdateBook_DuplicateIsbn_ThrowsException() {
        Book anotherBook = new Book("Animal Farm", "978-0451524935", "Satire", 1945, 9.99, mockAuthor);
        anotherBook.setId(2L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));
        when(bookRepository.findByIsbn("978-0451524935")).thenReturn(Optional.of(anotherBook));

        Book updateRequest = new Book("1984 Updated", "978-0451524935", "Dystopian", 1949, 14.99, mockAuthor);
        assertThatThrownBy(() -> bookService.updateBook(1L, updateRequest))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    // ---- DELETE ----
    @Test
    void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    // ---- COUNT ----
    @Test
    void testCountBooks() {
        when(bookRepository.count()).thenReturn(10L);
        assertThat(bookService.countBooks()).isEqualTo(10L);
    }
}
