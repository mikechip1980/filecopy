package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

public class SyncExecuteServiceFileTest {

    @Test
    public void executeTest() throws IOException {
            SyncExecuteService srv=new SyncExecuteServiceFile();
            srv.execute();
    }


}
