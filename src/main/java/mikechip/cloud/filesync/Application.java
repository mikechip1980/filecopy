package mikechip.cloud.filesync;
import mikechip.cloud.filesync.service.SyncExecuteService;
import mikechip.cloud.filesync.service.SyncExecuteServiceFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Application {
 //   private static final Logger logger
//            = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) throws IOException {
  //      logger.info("Start");
  //      logger.debug("Start");
        SyncExecuteService srv=new SyncExecuteServiceFile();
        srv.execute();
    }
}
