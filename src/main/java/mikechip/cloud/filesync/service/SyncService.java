package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.TransferPair;

import java.io.FileFilter;
import java.util.List;

public interface SyncService {
     List<TransferPair> buildTransferPairs(FileFilter filter);
}
