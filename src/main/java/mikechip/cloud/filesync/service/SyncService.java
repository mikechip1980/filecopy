package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.TransferPair;

import java.io.FileFilter;
import java.util.Collection;
import java.util.List;

public interface SyncService {
     List<TransferPair> buildTransferPairs(FileFilter filter);
     void buildTransferFolderPairs(String sourceFolder, FileFilter fileFilter, Collection<TransferPair> pairs, int folderNum);
}
