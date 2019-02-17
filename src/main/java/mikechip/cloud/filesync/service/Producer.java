package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.ApplicationContext;
import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.PairQueue;
import mikechip.cloud.filesync.dto.TransferPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.CountDownLatch;


public class Producer implements Runnable{
    private static final Logger logger
            = LoggerFactory.getLogger(Producer.class);
    private final PairQueue queue;
    private final String folder;
    private final int folderNum;
    private final Synchronizer sync;

    public Producer(PairQueue queue, String folder, int folderNum, Synchronizer sync) {
        this.queue = queue;
        this.folder = folder;
        this.folderNum = folderNum;
        this.sync=sync;
    }

    @Override
    public void run() {
        logger.debug("Producer start");
        SyncService syncService=ApplicationContext.getInstance().getSyncService();
        logger.debug("working with source folder "+folder,folder);
        syncService.buildTransferFolderPairs(folder,new FileFilter(), queue,folderNum);
        logger.debug("Producer end");
        logger.debug("Latch count:"+(sync.getMainThreadLatch().getCount()-1));
        int prCnt=sync.decreaseProducers();
        logger.debug("Producer count:"+prCnt);
        sync.getMainThreadLatch().countDown();
    }
}
