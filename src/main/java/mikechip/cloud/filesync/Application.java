package mikechip.cloud.filesync;
import mikechip.cloud.filesync.service.SyncExecuteService;
import mikechip.cloud.filesync.service.SyncExecuteServiceFile;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        SyncExecuteService srv=new SyncExecuteServiceFile();
        srv.execute();
    }
}
