package edu.ku.bookapi.controller;

import edu.ku.bookapi.model.Book;
import edu.ku.bookapi.model.BookInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final List<Book> books = new ArrayList<>(List.of(
            new Book(1L, "Java Programming", "Abdul Qahar", 5),
            new Book(2L, "Web Development", "Ahmad", 3),
            new Book(3L, "Database Systems", "Ali", 4)
    ));

    // 1. GET ALL BOOKS
    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    // 2. GET BOOK BY ID
    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(
            @PathVariable Long bookId
    ) {

        for (Book book : books) {

            if (book.id().equals(bookId)) {
                return ResponseEntity.ok(book);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // 3. PUT - UPDATE BOOK
    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookInput input
    ) {

        for (int i = 0; i < books.size(); i++) {

            Book book = books.get(i);

            if (book.id().equals(bookId)) {

                Book updatedBook = new Book(
                        book.id(),
                        input.title(),
                        input.author(),
                        input.availableCopies()
                );

                books.set(i, updatedBook);

                return ResponseEntity.ok(updatedBook);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // 4. DELETE BOOK
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(
            @PathVariable Long bookId
    ) {

        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).id().equals(bookId)) {

                books.remove(i);

                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }
}