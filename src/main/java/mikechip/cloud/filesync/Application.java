package mikechip.cloud.filesync;
import mikechip.cloud.filesync.config.ApplicationContext;
import mikechip.cloud.filesync.service.SyncExecuteService;
import mikechip.cloud.filesync.service.SyncExecuteServiceFileParallel;

import java.io.IOException;

public class Application {
 //   private static final Logger logger
//            = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) throws IOException {
  //      logger.info("Start");
  //      logger.debug("Start");
        ApplicationContext.getInstance();
        SyncExecuteService srv=new SyncExecuteServiceFileParallel();
        srv.execute();
    }
}
