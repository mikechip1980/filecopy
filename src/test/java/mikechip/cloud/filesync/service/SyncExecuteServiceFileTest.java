package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.ApplicationContext;
import mikechip.cloud.filesync.config.Config;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class SyncExecuteServiceFileTest {


    private static final Logger logger
            = LoggerFactory.getLogger(SyncExecuteServiceFileTest.class);
    private Config config=ApplicationContext.getInstance().getConfig();

    @Before
    public void init() throws IOException, ParseException {

    }


    @Test
    public void executeTestFromStartOfTimes() throws IOException, ParseException {
        logger.info("Start executeTestFromStartOfTimes");
            Config.resetLastSyncDate();
        logger.info("Cleaning folder");
            deleteFolder(new File(config.getDestPath()),1);
            SyncExecuteService srv=new SyncExecuteServiceFile();
            srv.execute();
        logger.info("End executeTestFromStartOfTimes");
    }

    @Test
    public void executeTestFromStartOfTimesParallel() throws IOException, ParseException {
        logger.info("Start executeTestFromStartOfTimesParallel");
        Config.resetLastSyncDate();
        logger.info("Cleaning folder");
        deleteFolder(new File(config.getDestPath()),1);
        SyncExecuteService srv=new SyncExecuteServicefileParallel();
        srv.execute();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        System.out.println("Main thread exiting.");
        logger.info("End executeTestFromStartOfTimesParallel");
    }


    public static void deleteFolder(File folder, int level) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f,level+1);
                } else {
                    f.delete();
                }
            }
        }
        if (level>1) folder.delete();
    }


}
