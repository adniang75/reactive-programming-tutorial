package com.alassaneniang.reactor.services;

import com.alassaneniang.reactor.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookServiceTest {

    private final BookInfoService bookInfoService = new BookInfoService();
    private final ReviewService reviewService = new ReviewService();

    private final BookService bookService = new BookService(bookInfoService, reviewService);

    @Test
    @DisplayName("Get Books")
    void getBooks() {
        Flux<Book> books = bookService.getBooks();
        StepVerifier.create(books)
                .assertNext(book -> {
                    assertEquals("Book One", book.getBookInfo().getTitle());
                    assertEquals(2, book.getReviews().size());
                })
                .assertNext(book -> {
                    assertEquals("Book Two", book.getBookInfo().getTitle());
                    assertEquals(2, book.getReviews().size());
                })
                .assertNext(book -> {
                    assertEquals("Book Three", book.getBookInfo().getTitle());
                    assertEquals(2, book.getReviews().size());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Get Book By Id")
    void getBookById() {
        Mono<Book> bookMono = bookService.getBookById(1);
        StepVerifier.create(bookMono)
                .assertNext(book -> {
                    assertEquals("Book One", book.getBookInfo().getTitle());
                    assertEquals(2, book.getReviews().size());
                })
                .verifyComplete();
    }

}