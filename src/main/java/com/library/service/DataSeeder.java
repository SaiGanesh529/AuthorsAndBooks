package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only seed if tables are empty
        if (authorRepository.count() == 0) {
            seedAuthors();
        }
        if (bookRepository.count() == 0) {
            seedBooks();
        }
    }

    private void seedAuthors() {
        Author[] authors = {
            new Author("George Orwell",       "British",    1903, "English novelist, essayist, journalist and critic."),
            new Author("J.K. Rowling",         "British",    1965, "Author of the Harry Potter fantasy series."),
            new Author("Fyodor Dostoevsky",    "Russian",    1821, "Novelist who explored human psychology and philosophy."),
            new Author("Gabriel Garcia Marquez","Colombian", 1927, "Author known for magical realism style."),
            new Author("Toni Morrison",        "American",   1931, "Nobel Prize-winning American novelist."),
            new Author("Haruki Murakami",      "Japanese",   1949, "Contemporary Japanese writer of surreal fiction."),
            new Author("Leo Tolstoy",          "Russian",    1828, "One of the greatest novelists of all time."),
            new Author("Virginia Woolf",       "British",    1882, "Modernist novelist and essayist."),
            new Author("Ernest Hemingway",     "American",   1899, "Nobel Prize winner known for his iceberg theory."),
            new Author("Jane Austen",          "British",    1775, "Novelist known for romance and social commentary.")
        };

        for (Author a : authors) {
            authorRepository.save(a);
        }
        System.out.println("✅ Seeded 10 authors into the database.");
    }

    private void seedBooks() {
        Author orwell      = authorRepository.findByNameIgnoreCase("George Orwell").orElseThrow();
        Author rowling     = authorRepository.findByNameIgnoreCase("J.K. Rowling").orElseThrow();
        Author dostoevsky  = authorRepository.findByNameIgnoreCase("Fyodor Dostoevsky").orElseThrow();
        Author marquez     = authorRepository.findByNameIgnoreCase("Gabriel Garcia Marquez").orElseThrow();
        Author morrison    = authorRepository.findByNameIgnoreCase("Toni Morrison").orElseThrow();
        Author murakami    = authorRepository.findByNameIgnoreCase("Haruki Murakami").orElseThrow();
        Author tolstoy     = authorRepository.findByNameIgnoreCase("Leo Tolstoy").orElseThrow();
        Author woolf       = authorRepository.findByNameIgnoreCase("Virginia Woolf").orElseThrow();
        Author hemingway   = authorRepository.findByNameIgnoreCase("Ernest Hemingway").orElseThrow();
        Author austen      = authorRepository.findByNameIgnoreCase("Jane Austen").orElseThrow();

        Book[] books = {
            new Book("1984",                         "978-0451524935", "Dystopian",   1949, 12.99, orwell),
            new Book("Harry Potter and the Sorcerer's Stone", "978-0439708180", "Fantasy", 1997, 14.99, rowling),
            new Book("Crime and Punishment",         "978-0486415871", "Psychological", 1866, 9.99,  dostoevsky),
            new Book("One Hundred Years of Solitude","978-0060883287", "Magical Realism", 1967, 15.99, marquez),
            new Book("Beloved",                      "978-1400033416", "Historical Fiction", 1987, 13.99, morrison),
            new Book("Norwegian Wood",               "978-0375704024", "Literary Fiction", 1987, 11.99, murakami),
            new Book("War and Peace",                "978-0199232765", "Historical Fiction", 1869, 19.99, tolstoy),
            new Book("Mrs Dalloway",                 "978-0156628709", "Modernist",    1925, 10.99, woolf),
            new Book("The Old Man and the Sea",      "978-0684801223", "Literary Fiction", 1952, 10.99, hemingway),
            new Book("Pride and Prejudice",          "978-0141439518", "Romance",      1813, 8.99,  austen)
        };

        for (Book b : books) {
            bookRepository.save(b);
        }
        System.out.println("✅ Seeded 10 books into the database.");
    }
}
