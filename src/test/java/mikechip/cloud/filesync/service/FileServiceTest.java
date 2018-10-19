package mikechip.cloud.filesync.service;


import mikechip.cloud.filesync.config.Config;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;


public class FileServiceTest {
    String srcFile, destFile;
    String fileName = "emptyTestFile.txt";
    Config config;

    @Before
    public void init() throws IOException, ParseException {
        Config.init();
        config = Config.getInstance();
        String srcFiles = config.getSourcePath() + "/" + fileName;
        String[] files = srcFiles.split(Config.PATH_DELIMITER);
        if (files != null && files.length > 0)
            srcFile = files[0]+"/"+fileName;
        destFile = config.getDestPath() + "/" + fileName;
        createEmptyFile();
    }

    @Test
    public void fileCopyIOTest() throws IOException {

        FileService srv = new FileService(new FileServiceIO());
        srv.copyFile(srcFile, destFile);
    }

    @Test
    public void fileCopyNIOTest() throws IOException {
        FileService srv = new FileService(new FileServiceNIO());
        srv.copyFile(srcFile, destFile);
    }

    private void createEmptyFile() throws IOException {
        File file = new File(srcFile);
        if (!file.exists()) {
            file.createNewFile();
            FileWriter fw = null;
            try {
                fw = new FileWriter(file);
                fw.write("Some test content");
                fw.flush();
            } finally {
                fw.close();
            }
        }
    }
}

