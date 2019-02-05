package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.ApplicationContext;
import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.PairQueue;
import mikechip.cloud.filesync.dto.TransferPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;



public class Producer implements Runnable{
    private static final Logger logger
            = LoggerFactory.getLogger(Producer.class);
    PairQueue queue;
    String folder;
    int folderNum;

    public Producer(PairQueue queue, String folder, int folderNum) {
        this.queue = queue;
        this.folder = folder;
        this.folderNum = folderNum;
    }

    @Override
    public void run() {
        logger.debug("Producer start");
        SyncService syncService=ApplicationContext.getInstance().getSyncService();
        logger.debug("working with source folder "+folder,folder);
        syncService.buildTransferFolderPairs(folder,new FileFilter(), queue,folderNum);
        logger.debug("Producer end");
    }
}
