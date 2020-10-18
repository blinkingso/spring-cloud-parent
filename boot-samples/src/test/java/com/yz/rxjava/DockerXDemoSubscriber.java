package com.yz.rxjava;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

/**
 * java 9 响应式编程入门demo
 *
 * @param <T>
 */
@Slf4j
public class DockerXDemoSubscriber<T> implements Flow.Subscriber<T> {

    private String name;
    // 保存了Subscriber和Publisher之间的关系Process接口
    private Flow.Subscription subscription;
    final long bufferSize;
    long count;

    public DockerXDemoSubscriber(String name, long bufferSize) {
        this.name = name;
        this.bufferSize = bufferSize;
    }

    /**
     * 在给定的Subscription想要使用Subscriber其他方法的前提下, 必须先调用这个方法
     *
     * @param subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        // 在消费一半的时候重新请求
        (this.subscription = subscription).request(bufferSize);
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (Exception e) {
            log.error("订阅logic异常: ", e);
        }
    }

    /**
     * 获取下一个Subscription的下一个元素
     *
     * @param item 下一个元素
     */
    @Override
    public void onNext(T item) {
        log.info(" ##### " + Thread.currentThread().getName() + " name : " + name + " item: " + item + " ######");
        log.info(name + " received: " + item);
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (Exception e) {
            log.error("消息处理异常: ", e);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("消息消费异常: ", throwable);
    }

    @Override
    public void onComplete() {
        log.info("消息全部消费完成, 执行onComplete方法");
    }
}
