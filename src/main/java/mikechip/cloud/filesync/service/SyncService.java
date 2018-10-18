package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.TransferPair;

import java.io.FileFilter;
import java.util.List;

public interface SyncService {
    //public SyncService getInstance();
    public List<TransferPair> buildTransferPairs(Config config, FileFilter filter);
}
