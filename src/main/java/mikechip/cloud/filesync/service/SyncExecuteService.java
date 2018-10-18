package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;

import java.io.IOException;

public interface SyncExecuteService {
    public void execute(Config config) throws IOException;
}
