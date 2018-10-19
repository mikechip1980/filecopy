package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.ApplicationException;
import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.config.PathNotReadyException;
import mikechip.cloud.filesync.dto.FilePair;
import mikechip.cloud.filesync.dto.TransferPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class SyncServiceFile implements SyncService {
    private static final Logger logger
            = LoggerFactory.getLogger(SyncServiceFile.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM");


    public List<TransferPair> buildTransferPairs(Config config, FileFilter filter){
        logger.debug("buildTransferPairs start");
        //we consider that config has only one folder at the moment
            List<TransferPair> pairs = new LinkedList();

            String[] srcFolders= config.getSourcePath().split(Config.PATH_DELIMITER);
            for (String srcFolder:srcFolders) {
                logger.debug("working with source folder "+srcFolder,srcFolder);
                buildTransferFolderPairs(srcFolder,config, filter, pairs);
            }
        logger.debug("buildTransferPairs end");
        return pairs;
    }

    private void buildTransferFolderPairs(String sourceFolder,Config config, FileFilter fileFilter, List<TransferPair> pairs){
        logger.debug("buildTransferFolderPairs start");
        String destFolder=config.getDestPath();
        File srcFileFolder= new File(sourceFolder);
        if (!srcFileFolder.isDirectory()) throw new ApplicationException("Source folder is not a Directory "+sourceFolder);
        if (!srcFileFolder.canRead()) throw new PathNotReadyException("Can not read source folder"+sourceFolder);

        File destFileFolder= new File(destFolder);
        if (!destFileFolder.isDirectory()) throw new ApplicationException("Dest folder is not a Directory "+destFolder);
        if (!destFileFolder.canWrite()) throw new PathNotReadyException("Can not read dest folder"+destFolder);

        File[] srcFileList =srcFileFolder.listFiles(fileFilter);
        logger.debug("Files after filter "+srcFileList.length);
        if (srcFileList!=null&&srcFileList.length>0)
            for (File srcFile:srcFileList){
                   File destFile=new File(buildDestinationPath(srcFile,destFolder),srcFile.getName());
                if (logger.isDebugEnabled())
                    logger.debug(String.format("File examned - source: %s; dest: %s",srcFile.getName(),destFile.getName() ));
                if (srcFile.isFile())
                       if (isCopyPair(srcFile,destFile)) {
                           pairs.add(new FilePair(srcFile, destFile));
                           if (logger.isDebugEnabled())
                           logger.debug("Pair added");
                        }
                   //implement recirsive logic here for subfolders
            }


        logger.debug("buildTransferFolderPairs end");
    }

    private boolean isCopyPair(File srcFile,File destFile){
        if (srcFile==null) return false;
        if (destFile==null) return false;
        if (!srcFile.getName().equals(destFile.getName())) return false;
        if (srcFile.length()!=destFile.length()) return true;
        if (!destFile.exists())  return true;
        return false;
    }

    private String buildDestinationPath(File srcFile, String destFolder) {
            //if (srcFile==null) throw new IllegalArgumentException("srcFile is null");
            String folder=destFolder+"/"+dateFormat.format(srcFile.lastModified());
          /*  File folderFile = new File(folder);
            if (!folderFile.exists())
                if (!folderFile.mkdir()) throw new PathNotReadyException("Can not create destination folder "+folder);
                */
            return folder;
    }
}
