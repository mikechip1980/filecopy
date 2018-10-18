package mikechip.cloud.filesync.config;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class ConfigTest {

    @Before
    public void init() throws IOException, ParseException {
        //Config.init();
    }

    @Test
    public void loadPropertiesFileTest() throws IOException, ParseException {
        Config.init();
        Config conf=Config.getInstance();
        assertNotNull(conf.getDestPath());
        assertNotNull(conf.getSourcePath());
    }

    @Test
    public void generateLastUpdateFileTest() throws IOException, ParseException {
        File lastUpdateFile=new File(Config.LAST_SYNC_FILE_NAME);
        if (lastUpdateFile.exists()) lastUpdateFile.delete();
        Config.init();
        assertTrue(lastUpdateFile.exists());
    }
}
