package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.ApplicationContext;
import mikechip.cloud.filesync.dto.PairQueue;
import mikechip.cloud.filesync.dto.TransferPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private static final Logger logger
            = LoggerFactory.getLogger(Consumer.class);

    private final PairQueue queue;
    private final Synchronizer sync;

    public Consumer(PairQueue queue,Synchronizer sync) {
        this.queue=queue;
        this.sync=sync;
    }


    @Override
    public void run() {
        logger.debug("Consumer start");
        TransferPair pair= null;
        FileService fileService = ApplicationContext.getInstance().getFileService();
        try {
        while (true) {
            //pair = queue.take();
            pair=queue.poll(50L, TimeUnit.MILLISECONDS);
            if (pair!=null) {
                if (logger.isDebugEnabled())
                    logger.debug("Coping from " + pair.getSourceName() + " to " + pair.getDestName());
                fileService.copyFile(pair.getSourceFile(), pair.getDestFile());
            }
            else {
                if (sync.getProducerCount()==0) {
                    logger.debug("Producers ended, completing");
                    Thread.currentThread().interrupt();
                }
            }
         }
        } catch (InterruptedException e) {
            logger.debug("Thread interrupted");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.debug("Consumer end");
        logger.debug("Latch count:"+(sync.getMainThreadLatch().getCount()-1));
        sync.getMainThreadLatch().countDown();
    }
}
