package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.ApplicationContext;
import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.PairQueue;
import mikechip.cloud.filesync.dto.TransferPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SyncExecuteServiceFileParallel implements SyncExecuteService {
    private static final Logger logger
            = LoggerFactory.getLogger(SyncServiceFile.class);

    final int CONSUMER_COUNT=2;

    @Override
    public void execute() throws IOException {
        logger.debug("execute start");
        PairQueue queue= new PairQueue();
        Config config=ApplicationContext.getInstance().getConfig();
        String[] srcFolders= config.getSourcePath().split(Config.PATH_DELIMITER);
        int producerCnt=srcFolders.length;
        int threadCnt=producerCnt+CONSUMER_COUNT;
        logger.debug("Overall thread count:"+threadCnt);
        Synchronizer sync= new Synchronizer(producerCnt, threadCnt);

        int i=0;
        List<Thread> list=new ArrayList(5);
        for (String srcFolder:srcFolders) {
            logger.debug("working with source folder "+srcFolder,srcFolder);
            Thread thread = new Thread(new Producer(queue,srcFolder,i++,sync));
            thread.start();
            list.add(thread);
        }

        for (int j=1;j<=CONSUMER_COUNT;j++){
            Thread thread = new Thread(new Consumer(queue,sync));
            thread.start();
            list.add(thread);
        }

        try {
            logger.debug("Latch count:"+sync.getMainThreadLatch().getCount());
            sync.getMainThreadLatch().await();
        } catch (InterruptedException e) {
            logger.error("Interrupted");
            Thread.currentThread().interrupt();
        }

        /*for (Thread th:list) {
            try {
                th.join();
            } catch (InterruptedException e) {
                logger.error("Interrupted");
            }
        }*/

        logger.debug("execute end");
    }
}
