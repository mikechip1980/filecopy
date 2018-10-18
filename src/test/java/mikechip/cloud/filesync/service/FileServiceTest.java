package mikechip.cloud.filesync.service;


import mikechip.cloud.filesync.config.Config;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;


public class FileServiceTest {
    String srcFile,destFile;
    String fileName="ideaIC-2018.1.5.tar.gz";
    Config config;

    @Before
    public void init() throws IOException, ParseException {
        Config.init();
        config=Config.getInstance();
        srcFile=config.getSourcePath()+"/"+fileName;
        destFile=config.getDestPath()+"/"+fileName;
    }

    @Test
    public  void fileCopyIOTest() throws IOException {

    FileService srv= new FileService(new FileServiceIO());
    srv.copyFile(srcFile,destFile);
    }

    @Test
    public  void fileCopyNIOTest() throws IOException {
        FileService srv= new FileService(new FileServiceNIO());
        srv.copyFile(srcFile,destFile);
    }
}
