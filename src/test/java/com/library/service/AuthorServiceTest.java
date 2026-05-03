package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author mockAuthor;

    @BeforeEach
    void setUp() {
        mockAuthor = new Author("George Orwell", "British", 1903, "English novelist.");
        mockAuthor.setId(1L);
    }

    // ---- SAVE ----
    @Test
    void testSaveAuthor_Success() {
        when(authorRepository.save(any(Author.class))).thenReturn(mockAuthor);

        Author saved = authorService.saveAuthor(mockAuthor);

        assertThat(saved.getName()).isEqualTo("George Orwell");
        verify(authorRepository, times(1)).save(mockAuthor);
    }

    // ---- GET ALL ----
    @Test
    void testGetAllAuthors() {
        Author author2 = new Author("Jane Austen", "British", 1775, "Novelist.");
        author2.setId(2L);
        when(authorRepository.findAll()).thenReturn(Arrays.asList(mockAuthor, author2));

        List<Author> result = authorService.getAllAuthors();

        assertThat(result).hasSize(2);
        verify(authorRepository, times(1)).findAll();
    }

    // ---- GET BY ID ----
    @Test
    void testGetAuthorById_Found() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(mockAuthor));

        Optional<Author> result = authorService.getAuthorById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("George Orwell");
    }

    @Test
    void testGetAuthorByIdOrThrow_NotFound_ThrowsException() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.getAuthorByIdOrThrow(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Author not found with id: 99");
    }

    // ---- UPDATE ----
    @Test
    void testUpdateAuthor_Success() {
        Author updatedData = new Author("George Orwell Updated", "English", 1903, "Updated bio.");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(mockAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(mockAuthor);

        Author result = authorService.updateAuthor(1L, updatedData);

        verify(authorRepository).save(any(Author.class));
        assertThat(mockAuthor.getName()).isEqualTo("George Orwell Updated");
    }

    @Test
    void testUpdateAuthor_NotFound_ThrowsException() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.updateAuthor(99L, mockAuthor))
                .isInstanceOf(RuntimeException.class);
    }

    // ---- DELETE ----
    @Test
    void testDeleteAuthor() {
        doNothing().when(authorRepository).deleteById(1L);

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }

    // ---- COUNT ----
    @Test
    void testCountAuthors() {
        when(authorRepository.count()).thenReturn(5L);

        long count = authorService.countAuthors();

        assertThat(count).isEqualTo(5L);
    }
}
