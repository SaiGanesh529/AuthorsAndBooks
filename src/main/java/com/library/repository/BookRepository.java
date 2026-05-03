package com.library.repository;

import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Find books by author ID
    List<Book> findByAuthorId(Long authorId);

    // Find books by genre
    List<Book> findByGenreIgnoreCase(String genre);

    // Find book by ISBN (unique)
    Optional<Book> findByIsbn(String isbn);

    // Find books by title containing keyword
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    /**
     * Custom INNER JOIN query between Books and Authors.
     * Returns a list of BookAuthorDTO containing combined data
     * from both tables.
     */
    @Query("SELECT new com.library.entity.BookAuthorDTO(" +
           "b.id, b.title, b.isbn, b.genre, b.publicationYear, b.price, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a")
    List<BookAuthorDTO> findAllBooksWithAuthors();

    /**
     * INNER JOIN filtered by genre.
     */
    @Query("SELECT new com.library.entity.BookAuthorDTO(" +
           "b.id, b.title, b.isbn, b.genre, b.publicationYear, b.price, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a WHERE LOWER(b.genre) = LOWER(:genre)")
    List<BookAuthorDTO> findBooksWithAuthorsByGenre(@Param("genre") String genre);

    /**
     * Native SQL INNER JOIN query — alternative approach.
     */
    @Query(value = "SELECT b.id AS bookId, b.title, b.isbn, b.genre, b.publication_year, " +
                   "b.price, a.id AS authorId, a.name AS authorName, a.nationality " +
                   "FROM books b INNER JOIN authors a ON b.author_id = a.id",
           nativeQuery = true)
    List<Object[]> findAllBooksWithAuthorsNative();
}
