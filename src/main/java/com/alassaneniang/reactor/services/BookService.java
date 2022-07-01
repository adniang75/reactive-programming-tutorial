package com.alassaneniang.reactor.services;

import com.alassaneniang.reactor.domain.Book;
import com.alassaneniang.reactor.domain.BookInfo;
import com.alassaneniang.reactor.domain.Review;
import com.alassaneniang.reactor.exception.BookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j(topic = "BookService")
public class BookService {

    public static final String EXCEPTION_OCCURRED_WHILE_FETCHING_BOOKS = "Exception occurred while fetching books";
    public static final String SUBSCRIBED = "Subscribed";
    public static final String ON_NEXT = "onNext -> {}";
    public static final String ERROR = "error -> {}";
    public static final String SEQUENCE_COMPLETE = "Sequence complete";
    public static final String EXCEPTION_IS = "Exception is: {}";

    private final BookInfoService bookInfoService;
    private final ReviewService reviewService;

    private static RetryBackoffSpec getRetryBackoffSpec() {
        return Retry
                .backoff(3, Duration.ofMillis(1000))
                .filter(BookException.class::isInstance)
                .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) ->
                        Exceptions.propagate(retrySignal.failure())));
    }

    public Flux<Book> getBooks() {
        Flux<BookInfo> allBooks = bookInfoService.getBooks();
        return allBooks
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews = reviewService
                            .getReviews(bookInfo.getBookId())
                            .collect(Collectors.toList());
                    return reviews
                            .map(review -> new Book(bookInfo, review));
                })
                .onErrorMap(throwable -> {
                    log.error(EXCEPTION_IS, throwable.getMessage());
                    return new BookException(EXCEPTION_OCCURRED_WHILE_FETCHING_BOOKS);
                })
                .doOnSubscribe(subscription -> log.info(SUBSCRIBED))
                .doOnNext(book -> log.info(ON_NEXT, book))
                .doOnError(error -> log.error(ERROR, error.getMessage()))
                .doOnComplete(() -> log.info(SEQUENCE_COMPLETE));
    }

    public Flux<Book> getBooksRetry() {
        Flux<BookInfo> allBooks = bookInfoService.getBooks();
        return allBooks
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews = reviewService
                            .getReviews(bookInfo.getBookId())
                            .collect(Collectors.toList());
                    return reviews
                            .map(review -> new Book(bookInfo, review));
                })
                .onErrorMap(throwable -> {
                    log.error(EXCEPTION_IS, throwable.getMessage());
                    return new BookException(EXCEPTION_OCCURRED_WHILE_FETCHING_BOOKS);
                })
                .retry(3)
                .doOnSubscribe(subscription -> log.info(SUBSCRIBED))
                .doOnNext(book -> log.info(ON_NEXT, book))
                .doOnError(error -> log.error(ERROR, error.getMessage()))
                .doOnComplete(() -> log.info(SEQUENCE_COMPLETE));
    }

    public Flux<Book> getBooksRetryWhen() {
        Flux<BookInfo> allBooks = bookInfoService.getBooks();
        return allBooks
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews = reviewService
                            .getReviews(bookInfo.getBookId())
                            .collect(Collectors.toList());
                    return reviews
                            .map(review -> new Book(bookInfo, review));
                })
                .onErrorMap(throwable -> {
                    log.error(EXCEPTION_IS, throwable.getMessage());
                    return new BookException(EXCEPTION_OCCURRED_WHILE_FETCHING_BOOKS);
                })
                .retryWhen(getRetryBackoffSpec())
                .doOnSubscribe(subscription -> log.info(SUBSCRIBED))
                .doOnNext(book -> log.info(ON_NEXT, book))
                .doOnError(error -> log.error(ERROR, error.getMessage()))
                .doOnComplete(() -> log.info(SEQUENCE_COMPLETE));
    }

    public Mono<Book> getBookById(long bookId) {
        Mono<BookInfo> bookInfo = bookInfoService.getBookById(bookId);
        Mono<List<Review>> reviews = reviewService.getReviews(bookId).collect(Collectors.toList());
        return bookInfo
                .zipWith(reviews, Book::new)
                .doOnSubscribe(subscription -> log.info(SUBSCRIBED))
                .doOnNext(book -> log.info(ON_NEXT, book))
                .doOnError(error -> log.error(ERROR, error.getMessage()));
    }

}
