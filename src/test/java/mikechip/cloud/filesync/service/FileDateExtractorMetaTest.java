package mikechip.cloud.filesync.service;

import com.drew.imaging.ImageProcessingException;
import mikechip.cloud.filesync.config.Config;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileDateExtractorMetaTest {
    private static final Logger logger
            = LoggerFactory.getLogger(FileDateExtractorMetaTest.class);

    String imageFileName = "P71223-113008.jpg";
    String videoFileName = "video.mp4";
    Config config;




    @Test
    public void printMetaInfoTest() throws ImageProcessingException, IOException {
        String srcFile = "/home/mikechip/Work/Java/sandbox/filecopy/source2/" + videoFileName;
        logger.info(srcFile);
        File file = new File(srcFile);
        FolderNameExtractorMeta extractor = new FolderNameExtractorMeta();
        extractor.printMetaInfo(file);
    }

    @Test
    public void extractImageDateTest() {
        String srcFile = "/home/mikechip/Work/Java/sandbox/filecopy/source/" + imageFileName;
        logger.info(srcFile);
        File file = new File(srcFile);
        FolderNameExtractorMeta extractor = new FolderNameExtractorMeta();
        Date date=extractor.get(file);
        logger.info(Config.dateFormat.format(date));
    }

    @Test
    public void getAnyFileDateTest() {
        String srcFolder = "/home/mikechip/Work/Java/sandbox/filecopy/source2/";
        File srcFolderFile = new File(srcFolder);

        for (File file : srcFolderFile.listFiles()) {
            logger.info(file.getAbsolutePath());
            FolderNameExtractorMeta extractor = new FolderNameExtractorMeta();
            Date date = extractor.get(file);
            logger.info(Config.dateFormat.format(date));
        }
    }

}
