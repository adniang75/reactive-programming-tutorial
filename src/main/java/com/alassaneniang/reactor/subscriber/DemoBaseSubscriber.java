package com.alassaneniang.reactor.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

@Slf4j(topic = "DemoBaseSubscriber")
public class DemoBaseSubscriber<T> extends BaseSubscriber<T> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        log.info("Subscribed!");
        request(3);
    }

    @Override
    protected void hookOnNext(T value) {
        log.info("value = {}", value);
        if (((int) value) == 3) {
            hookOnCancel();
        }
    }

    @Override
    protected void hookOnComplete() {
        log.info("Completed!");
    }

    @Override
    protected void hookOnCancel() {
        log.info("Cancelled!");
    }

}
