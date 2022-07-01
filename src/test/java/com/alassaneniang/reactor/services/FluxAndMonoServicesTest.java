package com.alassaneniang.reactor.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoServicesTest {

    FluxAndMonoServices fluxAndMonoServices
            = new FluxAndMonoServices();

    @Test
    @DisplayName("Fruit Flux Test")
    void fruitFluxTest() {
        Flux<String> fruitsFlux = fluxAndMonoServices.fruitsFlux();
        StepVerifier.create(fruitsFlux)
                .expectNext("Mango", "Orange", "Banana")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Mono Test")
    void fruitMonoTest() {
        Mono<String> fruitMono = fluxAndMonoServices.fruitMono();
        StepVerifier.create(fruitMono)
                .expectNext("Apple")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Map Test")
    void fruitFluxMapTest() {
        Flux<String> fruitsFluxMap = fluxAndMonoServices.fruitsFluxMap();
        StepVerifier.create(fruitsFluxMap)
                .expectNext("MANGO", "ORANGE", "BANANA")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Filter Test")
    void fruitFluxFilterTest() {
        Flux<String> fruitsFluxFilter = fluxAndMonoServices.fruitsFluxFilter(5);
        StepVerifier.create(fruitsFluxFilter)
                .expectNext("Orange", "Banana")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Filter Map Test")
    void fruitFluxFilterMapTest() {
        Flux<String> fruitsFluxFilterMap = fluxAndMonoServices.fruitsFluxFilterMap(5);
        StepVerifier.create(fruitsFluxFilterMap)
                .expectNext("ORANGE", "BANANA")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux FlatMap Test")
    void fruitFluxFlatMapTest() {
        Flux<String> fruitsFluxFlatMap = fluxAndMonoServices.fruitsFluxFlatMap();
        StepVerifier.create(fruitsFluxFlatMap)
                .expectNextCount("Mango".length() + "Orange".length() + "Banana".length())
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux FlatMap Async Test")
    void fruitFluxFlatMapAsyncTest() {
        Flux<String> fruitsFluxFlatMapAsync = fluxAndMonoServices.fruitsFluxFlatMapAsync();
        StepVerifier.create(fruitsFluxFlatMapAsync)
                .expectNextCount("Mango".length() + "Orange".length() + "Banana".length())
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Mono FlatMap Test")
    void fruitMonoFlatMapTest() {
        Mono<List<String>> fruitMonoFlatMap = fluxAndMonoServices.fruitMonoFlatMap();
        StepVerifier.create(fruitMonoFlatMap)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Concat Async Test")
    void fruitFluxConcatMapAsyncTest() {
        Flux<String> fruitsFluxFlatMapAsync = fluxAndMonoServices.fruitsFluxConcatMap();
        StepVerifier.create(fruitsFluxFlatMapAsync)
                .expectNextCount("Mango".length() + "Orange".length() + "Banana".length())
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Mono FlatMapMany")
    void fruitMonoFlatMapMany() {
        Flux<String> fruitMonoFlatMapMany = fluxAndMonoServices.fruitMonoFlatMapMany();
        StepVerifier.create(fruitMonoFlatMapMany)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Transform Test")
    void fruitFluxTransformTest() {
        Flux<String> fruitsFluxTransform = fluxAndMonoServices.fruitsFluxTransform(5);
        StepVerifier.create(fruitsFluxTransform)
                .expectNext("Orange", "Banana")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Transform Default If Empty Test")
    void fruitFluxTransformDefaultIfEmptyTest() {
        Flux<String> fruitsFluxTransformDefaultIfEmpty = fluxAndMonoServices.fruitsFluxTransformDefaultIfEmpty(10);
        StepVerifier.create(fruitsFluxTransformDefaultIfEmpty)
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Transform Switch If Empty Test")
    void fruitFluxTransformSwitchIfEmptyTest() {
        Flux<String> fruitsFluxTransformSwitchIfEmpty = fluxAndMonoServices.fruitsFluxTransformSwitchIfEmpty(8);
        StepVerifier.create(fruitsFluxTransformSwitchIfEmpty)
                .expectNext("Pineapple", "Jack Fruit")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Concat Test")
    void fruitFluxConcatTest() {
        Flux<String> fruitsFluxConcat = fluxAndMonoServices.fruitsFluxConcat();
        StepVerifier.create(fruitsFluxConcat)
                .expectNext("Mango", "Orange", "Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux ConcatWith Test")
    void fruitFluxConcatWithTest() {
        Flux<String> fruitsFluxConcatWith = fluxAndMonoServices.fruitsFluxConcatWith();
        StepVerifier.create(fruitsFluxConcatWith)
                .expectNext("Mango", "Orange", "Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Mono ConcatWith Test")
    void fruitMonoConcatWithTest() {
        Flux<String> fruitsMonoConcatWith = fluxAndMonoServices.fruitsMonoConcatWith();
        StepVerifier.create(fruitsMonoConcatWith)
                .expectNext("Mango", "Tomato")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Merge Test")
    void fruitFluxMergeTest() {
        Flux<String> fruitsFluxMerge = fluxAndMonoServices.fruitsFluxMerge();
        StepVerifier.create(fruitsFluxMerge)
                .expectNext("Mango", "Tomato", "Orange", "Lemon")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux MergeWith Test")
    void fruitFluxMergeWithTest() {
        Flux<String> fruitsFluxMergeWith = fluxAndMonoServices.fruitsFluxMergeWith();
        StepVerifier.create(fruitsFluxMergeWith)
                .expectNext("Mango", "Tomato", "Orange", "Lemon")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Merge Sequential Test")
    void fruitFluxMergeSequentialTest() {
        Flux<String> fruitsFluxMergeSequential = fluxAndMonoServices.fruitsFluxMergeSequential();
        StepVerifier.create(fruitsFluxMergeSequential)
                .expectNext("Mango", "Orange", "Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Zip Test")
    void fruitFluxZipTest() {
        Flux<String> fruitsFluxZip = fluxAndMonoServices.fruitsFluxZip();
        StepVerifier.create(fruitsFluxZip)
                .expectNext("MangoTomato", "OrangeLemon")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux ZipWith Test")
    void fruitFluxZipWithTest() {
        Flux<String> fruitsFluxZipWith = fluxAndMonoServices.fruitsFluxZipWith();
        StepVerifier.create(fruitsFluxZipWith)
                .expectNext("MangoTomato", "OrangeLemon")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Zip Tuple Test")
    void fruitFluxZipTupleTest() {
        Flux<String> fruitsFluxZipTuple = fluxAndMonoServices.fruitsFluxZipTuple();
        StepVerifier.create(fruitsFluxZipTuple)
                .expectNext("MangoTomatoPotato", "OrangeLemonBeans")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux Filter DoOn Test")
    void fruitFluxFilterDoOnTest() {
        Flux<String> fruitsFluxFilterDoOn = fluxAndMonoServices.fruitsFluxFilterDoOn(5);
        StepVerifier.create(fruitsFluxFilterDoOn)
                .expectNext("Orange", "Banana")
                .verifyComplete();
    }


    @Test
    @DisplayName("Fruit Flux OnError Return Test")
    void fruitFluxOnErrorReturnTest() {
        Flux<String> fruitsFluxOnErrorReturn = fluxAndMonoServices.fruitsFluxOnErrorReturn();
        StepVerifier.create(fruitsFluxOnErrorReturn)
                .expectNext("Apple", "Mango", "Orange")
                .verifyComplete();
    }

    @Test
    @DisplayName("Fruit Flux OnError Continue Test")
    void fruitFluxOnErrorContinueTest() {
        Flux<String> fruitsFluxOnErrorContinue = fluxAndMonoServices.fruitsFluxOnErrorContinue();
        StepVerifier.create(fruitsFluxOnErrorContinue)
                .expectNext("APPLE", "ORANGE")
                .verifyComplete();
    }

}