package com.alassaneniang.reactor.services;

import com.alassaneniang.reactor.domain.Book;
import com.alassaneniang.reactor.exception.BookException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceMockTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookInfoService bookInfoService;
    @Mock
    private ReviewService reviewService;

    @Test
    @DisplayName("Get Books Mock Test")
    void getBooksMockTest() {
        when(bookInfoService.getBooks())
                .thenCallRealMethod();
        when(reviewService.getReviews(anyLong()))
                .thenCallRealMethod();
        Flux<Book> books = bookService.getBooks();
        StepVerifier.create(books)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @DisplayName("Get Books Mock On Error Test")
    void getBooksMockOnErrorTest() {
        when(bookInfoService.getBooks())
                .thenCallRealMethod();
        when(reviewService.getReviews(anyLong()))
                .thenThrow(new IllegalArgumentException("exception using test"));
        Flux<Book> books = bookService.getBooks();
        StepVerifier.create(books)
                .expectError(BookException.class)
                .verify();
    }

    @Test
    @DisplayName("Get Books Retry Test")
    void getBooksRetryTest() {
        when(bookInfoService.getBooks())
                .thenCallRealMethod();
        when(reviewService.getReviews(anyLong()))
                .thenThrow(new IllegalArgumentException("exception using test"));
        Flux<Book> books = bookService.getBooksRetry();
        StepVerifier.create(books)
                .expectError(BookException.class)
                .verify();
    }

    @Test
    @DisplayName("Get Books Retry When Test")
    void getBooksRetryWhenTest() {
        when(bookInfoService.getBooks())
                .thenCallRealMethod();
        when(reviewService.getReviews(anyLong()))
                .thenThrow(new IllegalArgumentException("exception using test"));
        Flux<Book> books = bookService.getBooksRetryWhen();
        StepVerifier.create(books)
                .expectError(BookException.class)
                .verify();
    }

}