package mikechip.cloud.filesync.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.mp4.Mp4Directory;
import mikechip.cloud.filesync.config.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FolderNameExtractorMeta implements FileDateExtractor {


    private static final Logger logger
            = LoggerFactory.getLogger(FolderNameExtractorMeta.class);
    @Override
    public Date get(File srcFile) {
        return extractDate(srcFile);
    }

    public void printMetaInfo(File file) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);

        for (Directory directory : metadata.getDirectories()) {
            logger.info("Directory Name:"+directory.getName());
            for (Tag tag : directory.getTags()) {
                logger.info(tag.toString());
            }
        }

    }


    private Date extractDate(File file)  {
        Metadata metadata = null;
        int tagNumber=0;
        Directory directory=null;
        Date date=null;
        try {
            metadata = ImageMetadataReader.readMetadata(file);


            if (metadata.containsDirectoryOfType(ExifSubIFDDirectory.class)) {
                tagNumber = ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL;
                directory=metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                logger.debug("ExifSubIFDDirectory found");
            }
            else if (metadata.containsDirectoryOfType(Mp4Directory.class)) {
                directory=metadata.getFirstDirectoryOfType(Mp4Directory.class);
                tagNumber = Mp4Directory.TAG_CREATION_TIME;
                logger.debug("Mp4Directory found");
            }
            if (directory!=null)
                date = directory.getDate(tagNumber);

        } catch (ImageProcessingException e) {
            logger.error("Failed to get format, file:"+file.getAbsolutePath()+"; Error:"+e.getMessage());
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        if (date==null) {
            date = new Date(file.lastModified());
            logger.debug("File date taken");
        }
        else
            logger.debug("Meta date taken");

            logger.debug("File date:"+date);
            return date;
    }


}
