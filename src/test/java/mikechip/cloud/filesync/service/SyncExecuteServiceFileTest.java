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
        long start_time = System.nanoTime();
            SyncExecuteService srv=new SyncExecuteServiceFile();
            srv.execute();
        long end_time = System.nanoTime();
        double difference = (end_time - start_time) / 1e6;
        System.out.println("Main thread exiting. Time:"+difference);
        logger.info("End executeTestFromStartOfTimes");
    }

    @Test
    public void executeTestFromStartOfTimesParallel() throws IOException, ParseException {
        logger.info("Start executeTestFromStartOfTimesParallel");
        Config.resetLastSyncDate();
        logger.info("Cleaning folder");
        deleteFolder(new File(config.getDestPath()),1);
        long start_time = System.nanoTime();
        SyncExecuteService srv=new SyncExecuteServiceFileParallel();
        srv.execute();
        long end_time = System.nanoTime();
        double difference = (end_time - start_time) / 1e6;
        System.out.println("Main thread exiting. Time:"+difference);
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
