package mikechip.cloud.filesync.service;

import com.drew.imaging.ImageProcessingException;
import mikechip.cloud.filesync.config.Config;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class FolderNameExtractorMetaTest {
    private static final Logger logger
            = LoggerFactory.getLogger(FolderNameExtractorMetaTest.class);

    String imageFileName = "image.jpg";
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
    public void extractImageDateTest() throws ImageProcessingException, IOException {
        String srcFile = "/home/mikechip/Work/Java/sandbox/filecopy/source2/" + imageFileName;
        logger.info(srcFile);
        File file = new File(srcFile);
        FolderNameExtractorMeta extractor = new FolderNameExtractorMeta();
        Date date=extractor.extractImageDate(file);
        logger.info(Config.dateFormat.format(date));
    }

}
