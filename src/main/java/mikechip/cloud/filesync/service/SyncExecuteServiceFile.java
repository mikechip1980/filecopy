package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.ApplicationContext;
import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.TransferPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class SyncExecuteServiceFile implements SyncExecuteService {
    private static final Logger logger
            = LoggerFactory.getLogger(SyncServiceFile.class);

    public void execute() throws IOException {
        logger.debug("execute start");
        Config config=ApplicationContext.getInstance().getConfig();
        SyncService syncService=ApplicationContext.getInstance().getSyncService();
        FileFilter fileFilter=new FileFilter();
        Collection<TransferPair> pairs=syncService.buildTransferPairs(fileFilter);

        FileService fileService = ApplicationContext.getInstance().getFileService();
        for (TransferPair pair:pairs) {
            if (logger.isDebugEnabled())
            logger.debug("Coping from "+pair.getSourceName()+" to "+pair.getDestName());
            fileService.copyFile(pair.getSourceFile(),pair.getDestFile());
        }
        config.updateLastSyncDate();
        logger.debug("execute end");
    }
}
