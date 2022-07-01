package com.alassaneniang.reactor.services;

import com.alassaneniang.reactor.domain.BookInfo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BookInfoService {

    public Flux<BookInfo> getBooks() {
        List<BookInfo> books = List.of(
                new BookInfo(1, "Book One", "Author One", "12121212"),
                new BookInfo(2, "Book Two", "Author Two", "42342343"),
                new BookInfo(3, "Book Three", "Author Three", "23425444")
        );
        return Flux.fromIterable(books);
    }

    public Mono<BookInfo> getBookById(long bookId) {
        BookInfo book = new BookInfo(bookId, "Book One", "Author One", "12121212");
        return Mono.just(book);
    }

}
