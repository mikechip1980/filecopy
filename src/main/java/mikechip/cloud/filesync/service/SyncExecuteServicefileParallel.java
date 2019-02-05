package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.ApplicationContext;
import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.PairQueue;
import mikechip.cloud.filesync.dto.TransferPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SyncExecuteServicefileParallel implements SyncExecuteService {
    private static final Logger logger
            = LoggerFactory.getLogger(SyncServiceFile.class);


    final int CONSUMER_COUNT=1;

    @Override
    public void execute() throws IOException {
        logger.debug("execute start");
        PairQueue queue= new PairQueue();
        Config config=ApplicationContext.getInstance().getConfig();
        String[] srcFolders= config.getSourcePath().split(Config.PATH_DELIMITER);
        int i=0;
        for (String srcFolder:srcFolders) {
            logger.debug("working with source folder "+srcFolder,srcFolder);
            Thread thread = new Thread(new Producer(queue,srcFolder,i++));
            thread.start();
        }

        for (int j=1;j<=CONSUMER_COUNT;j++){
            Thread thread = new Thread(new Consumer(queue));
            thread.start();
        }

        logger.debug("execute end");
    }
}
