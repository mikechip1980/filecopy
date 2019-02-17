package mikechip.cloud.filesync.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Synchronizer {
    private volatile int producerCount;
    private final CountDownLatch mainThreadLatch;

    public Synchronizer(int producerCount, int OverallCount) {
        this.producerCount = producerCount;
        this.mainThreadLatch = new CountDownLatch(OverallCount);
    }

    public synchronized int getProducerCount() {
        return producerCount;
    }

    public synchronized int decreaseProducers(){
       return producerCount--;
    }

    public CountDownLatch getMainThreadLatch() {
        return mainThreadLatch;
    }
}
