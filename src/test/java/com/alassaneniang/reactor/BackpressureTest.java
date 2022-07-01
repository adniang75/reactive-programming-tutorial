package com.alassaneniang.reactor;

import com.alassaneniang.reactor.subscriber.DemoBaseSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j(topic = "BackpressureTest")
class BackpressureTest {

    @Test
    @DisplayName("Back Pressure Test")
    void backPressureTest() {
        Flux<Integer> numbers = Flux.range(1, 100);
        numbers.subscribe(new DemoBaseSubscriber<>());
        StepVerifier.create(numbers)
                .expectNextCount(100) // took 3 and drop the rest
                .verifyComplete();
    }

    @Test
    @DisplayName("Back Pressure Drop Test")
    void backPressureDropTest() {
        Flux<Integer> numbers = Flux.range(1, 100);
        numbers
                .onBackpressureDrop(integer -> log.info("Dropped values = {}", integer))
                .subscribe(new DemoBaseSubscriber<>());
        StepVerifier.create(numbers)
                .expectNextCount(100) // took 3 and drop the rest
                .verifyComplete();
    }

}
