package mikechip.cloud.filesync;

import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.service.SyncExecuteService;
import mikechip.cloud.filesync.service.SyncExecuteServiceFile;

import java.io.IOException;
import java.text.ParseException;

public class Application {
    public static void main(String[] args) throws IOException, ParseException {
        Config.init();
        Config config=Config.getInstance();
        SyncExecuteService srv=new SyncExecuteServiceFile();
        srv.execute(config);
    }
}
