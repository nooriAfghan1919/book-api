package edu.ku.bookapi.controller;

import edu.ku.bookapi.model.BookInput;
import edu.ku.bookapi.model.Books;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    // زموږ موقتي کتابونه
    private final List<Books> books =
            new CopyOnWriteArrayList<>(
                    List.of(
                            new Books(
                                    101L,
                                    "Clean Code",
                                    "Robert C. Martin",
                                    "9780132350884",
                                    2008,
                                    "Software Engineering"
                            ),

                            new Books(
                                    102L,
                                    "Effective Java",
                                    "Joshua Bloch",
                                    "9780134685991",
                                    2018,
                                    "Java"
                            ),

                            new Books(
                                    103L,
                                    "Spring in Action",
                                    "Craig Walls",
                                    "9781617297571",
                                    2022,
                                    "Spring"
                            )
                    )
            );

    // د نوي Book لپاره راتلونکې ID
    private final AtomicLong nextId = new AtomicLong(104);


    // =====================================================
    // GET - ټول Books
    // =====================================================

    @GetMapping
    public List<Books> getAllBooks() {

        return books;
    }


    // =====================================================
    // GET - د ID له مخې یو Book
    // =====================================================

    @GetMapping("/{id}")
    public Books getBookById(@PathVariable Long id) {

        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }


    // =====================================================
    // POST - نوی Book اضافه کول
    // =====================================================

    @PostMapping
    public Books createBook(@RequestBody BookInput input) {

        Long newId = nextId.getAndIncrement();

        Books newBook = new Books(
                newId,
                input.getTitle(),
                input.getAuthor(),
                input.getIsbn(),
                input.getPublishedYear(),
                input.getCategory()
        );

        books.add(newBook);

        return newBook;
    }
}