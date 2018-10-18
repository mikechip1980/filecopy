package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.TransferPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class SyncExecuteServiceFile implements SyncExecuteService {
    private static final Logger logger
            = LoggerFactory.getLogger(SyncServiceFile.class);

    public void execute(Config config) throws IOException {
        logger.debug("execute start");
        SyncService syncService=new SyncServiceFile();
        FileFilter fileFilter=new FileFilter(config);
        List<TransferPair> pairs=syncService.buildTransferPairs(config,fileFilter);

        FileService fileService = new FileService(new FileServiceNIO());
        for (TransferPair pair:pairs) {
            if (logger.isDebugEnabled())
            logger.debug("Coping from "+pair.getSourceName()+" to "+pair.getDestName());
            fileService.copyFile(pair.getSourceFile(),pair.getDestFile());
        }
        config.updateLastSyncDate();
        logger.debug("execute end");
    }
}
