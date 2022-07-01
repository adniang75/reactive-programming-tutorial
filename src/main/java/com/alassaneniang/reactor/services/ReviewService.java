package com.alassaneniang.reactor.services;

import com.alassaneniang.reactor.domain.Review;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ReviewService {

    public Flux<Review> getReviews(long bookId) {
        List<Review> reviewList = List.of(
                new Review(1, bookId, 9.1, "Good Book"),
                new Review(2, bookId, 8.6, "Worth Reading")
        );
        return Flux.fromIterable(reviewList);
    }

}
