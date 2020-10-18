package com.yz.rxjava;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 * 消息发布者
 * <p>
 * 发送消息并推送给消息消费者
 *
 * @param <T>
 */
@Slf4j
public class DockerXDemoPublisher<T> implements Flow.Publisher<T> {

    // daemon-based Thread pool
    private final ExecutorService executorService;

    // 订阅者关系信息列表
    private CopyOnWriteArrayList<DockerXDemoSubscription> subscriptions = new CopyOnWriteArrayList<>();

    public DockerXDemoPublisher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void submit(T item) {
        log.info("************ 开始发布元素 item :  " + item + " ****************");
        this.subscriptions.forEach(e -> e.future = this.executorService.submit(() -> e.subscriber.onNext(item)));
    }

    /**
     * 所有的Subscription完成
     */
    public void close() {
        subscriptions.forEach(e -> e.future = this.executorService.submit(() -> e.subscriber.onComplete()));
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        // 发布时, 首次发布将订阅
        subscriber.onSubscribe(new DockerXDemoSubscription(subscriber, executorService));
        this.subscriptions.add(new DockerXDemoSubscription(subscriber, executorService));
    }

    static class DockerXDemoSubscription<T> implements Flow.Subscription {

        private final Flow.Subscriber<? super T> subscriber;
        private final ExecutorService executorService;
        private Future<?> future;
        private boolean completed;
        private T item;

        DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executorService) {
            this.subscriber = subscriber;
            this.executorService = executorService;
        }

        @Override
        public void request(long n) {
            if (n != 0 && !completed) {
                if (n < 0) {
                    IllegalArgumentException exception = new IllegalArgumentException();
                    this.executorService.execute(() -> this.subscriber.onError(exception));
                } else {
                    future = this.executorService.submit(() -> this.subscriber.onNext(item));
                }
            } else {
                // 任务执行完成
                this.subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            this.completed = true;
            if (this.future != null && !this.future.isCancelled()) {
                this.future.cancel(true);
            }
        }
    }
}
