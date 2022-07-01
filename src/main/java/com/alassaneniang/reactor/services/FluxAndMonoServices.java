package com.alassaneniang.reactor.services;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Slf4j(topic = "FluxAndMonoServices")
public class FluxAndMonoServices {

    private static final List<String> fruits = List.of("Mango", "Orange", "Banana");

    public Flux<String> fruitsFlux() {
        return Flux.fromIterable(fruits);
    }

    public Flux<String> fruitsFluxMap() {
        return Flux.fromIterable(fruits)
                .map(String::toUpperCase);
    }

    public Flux<String> fruitsFluxFilter(int length) {
        return Flux.fromIterable(fruits)
                .filter(fruit -> fruit.length() > length);
    }

    public Flux<String> fruitsFluxFilterMap(int length) {
        return Flux.fromIterable(fruits)
                .filter(fruit -> fruit.length() > length)
                .map(String::toUpperCase);
    }

    public Mono<String> fruitMono() {
        return Mono.just("Apple");
    }

    public Flux<String> fruitsFluxFlatMap() {
        return Flux.fromIterable(fruits)
                .flatMap(fruit -> Flux.just(fruit.split("")));
    }

    public Flux<String> fruitsFluxFlatMapAsync() {
        return Flux.fromIterable(fruits)
                .flatMap(fruit -> Flux.just(fruit.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    }

    public Mono<List<String>> fruitMonoFlatMap() {
        return Mono.just("Mango")
                .flatMap(fruit -> Mono.just(List.of(fruit.split(""))));
    }

    public Flux<String> fruitsFluxConcatMap() {
        return Flux.fromIterable(fruits)
                .concatMap(fruit -> Flux.just(fruit.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    }

    public Flux<String> fruitMonoFlatMapMany() {
        return Mono.just("Mango")
                .flatMapMany(fruit -> Flux.just(fruit.split("")));
    }

    public Flux<String> fruitsFluxTransform(int length) {
        Function<Flux<String>, Flux<String>> filterData
                = data -> data.filter(fruit -> fruit.length() > length);
        return Flux.fromIterable(fruits)
                .transform(filterData);
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(int length) {
        Function<Flux<String>, Flux<String>> filterData
                = data -> data.filter(fruit -> fruit.length() > length);
        return Flux.fromIterable(fruits)
                .transform(filterData)
                .defaultIfEmpty("Default");
    }

    public Flux<String> fruitsFluxTransformSwitchIfEmpty(int length) {
        Function<Flux<String>, Flux<String>> filterData
                = data -> data.filter(fruit -> fruit.length() > length);
        return Flux.fromIterable(fruits)
                .transform(filterData)
                .switchIfEmpty(
                        Flux.just("Pineapple", "Jack Fruit")
                                .transform(filterData)
                );
    }

    public Flux<String> fruitsFluxConcat() {
        Flux<String> fruitsFlux = Flux.just("Mango", "Orange");
        Flux<String> veggiesFlux = Flux.just("Tomato", "Lemon");
        return Flux.concat(fruitsFlux, veggiesFlux);
    }

    public Flux<String> fruitsFluxConcatWith() {
        Flux<String> fruitsFlux = Flux.just("Mango", "Orange");
        Flux<String> veggiesFlux = Flux.just("Tomato", "Lemon");
        return fruitsFlux.concatWith(veggiesFlux);
    }

    public Flux<String> fruitsMonoConcatWith() {
        Mono<String> fruitMono = Mono.just("Mango");
        Mono<String> veggieMono = Mono.just("Tomato");
        return fruitMono.concatWith(veggieMono);
    }

    public Flux<String> fruitsFluxMerge() {
        Flux<String> fruits = Flux.just("Mango", "Orange").delayElements(Duration.ofMillis(50));
        Flux<String> veggies = Flux.just("Tomato", "Lemon").delayElements(Duration.ofMillis(75));
        return Flux.merge(fruits, veggies);
    }

    public Flux<String> fruitsFluxMergeWith() {
        Flux<String> fruits = Flux.just("Mango", "Orange").delayElements(Duration.ofMillis(50));
        Flux<String> veggies = Flux.just("Tomato", "Lemon").delayElements(Duration.ofMillis(75));
        return fruits.mergeWith(veggies);
    }

    public Flux<String> fruitsFluxMergeSequential() {
        Flux<String> fruits = Flux.just("Mango", "Orange").delayElements(Duration.ofMillis(50));
        Flux<String> veggies = Flux.just("Tomato", "Lemon").delayElements(Duration.ofMillis(75));
        return Flux.mergeSequential(fruits, veggies);
    }

    public Flux<String> fruitsFluxZip() {
        Flux<String> fruits = Flux.just("Mango", "Orange");
        Flux<String> veggies = Flux.just("Tomato", "Lemon");
        return Flux.zip(
                fruits, veggies,
                (fruit, veggie) -> fruit + veggie
        );
    }

    public Flux<String> fruitsFluxZipWith() {
        Flux<String> fruits = Flux.just("Mango", "Orange");
        Flux<String> veggies = Flux.just("Tomato", "Lemon");
        return fruits.zipWith(veggies, (fruit, veggie) -> fruit + veggie);
    }

    public Flux<String> fruitsFluxZipTuple() {
        Flux<String> fruits = Flux.just("Mango", "Orange");
        Flux<String> veggies = Flux.just("Tomato", "Lemon");
        Flux<String> moreVeggies = Flux.just("Potato", "Beans");
        return Flux.zip(fruits, veggies, moreVeggies)
                .map(objects -> objects.getT1() + objects.getT2() + objects.getT3());
    }

    public Flux<String> fruitsFluxFilterDoOn(int length) {
        return Flux.fromIterable(fruits)
                .filter(fruit -> fruit.length() > length)
                .doOnNext(fruit -> log.info("onNext -> {}", fruit))
                .doOnSubscribe(subscription -> log.info("subscription -> Done"))
                .doOnComplete(() -> log.info("Sequence complete"));
    }

    public Flux<String> fruitsFluxOnErrorReturn() {
        return Flux.just("Apple", "Mango")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .onErrorReturn("Orange")
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .doOnNext(fruit -> log.info("onNext -> {}", fruit))
                .doOnError(error -> log.error("error -> {}", error.getMessage()))
                .doOnComplete(() -> log.info("Sequence completed"));
    }

    public Flux<String> fruitsFluxOnErrorContinue() {
        return Flux.just("Apple", "Mango", "Orange")
                .map(fruit -> {
                    if (fruit.equalsIgnoreCase("Mango")) {
                        throw new RuntimeException("Exception Occurred");
                    }
                    return fruit.toUpperCase();
                })
                .onErrorContinue((error, object) -> {
                    log.error("error -> {}", error);
                    log.info("object -> {}", object);
                })
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .doOnNext(fruit -> log.info("onNext -> {}", fruit))
                .doOnError(error -> log.error("error -> {}", error.getMessage()))
                .doOnComplete(() -> log.info("Sequence completed"));
    }

}
