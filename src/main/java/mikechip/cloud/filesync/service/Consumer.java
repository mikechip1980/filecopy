package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.ApplicationContext;
import mikechip.cloud.filesync.dto.PairQueue;
import mikechip.cloud.filesync.dto.TransferPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Consumer implements Runnable {
    private static final Logger logger
            = LoggerFactory.getLogger(Consumer.class);

    PairQueue queue;

    public Consumer(PairQueue queue) {
        this.queue=queue;
    }


    @Override
    public void run() {
        logger.debug("Consumer start");
        TransferPair pair= null;
        FileService fileService = ApplicationContext.getInstance().getFileService();
        try {
        while (true) {
            pair = queue.take();
            if (logger.isDebugEnabled())
                    logger.debug("Coping from " + pair.getSourceName() + " to " + pair.getDestName());
            fileService.copyFile(pair.getSourceFile(), pair.getDestFile());
        }
        } catch (InterruptedException e) {
            logger.debug("Thread interrupted");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.debug("Consumer end");
    }
}
