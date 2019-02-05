package mikechip.cloud.filesync.service;


import mikechip.cloud.filesync.config.ApplicationContext;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SyncServiceFile implements SyncService {
    private static final Logger logger
            = LoggerFactory.getLogger(SyncServiceFile.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM");
    FileDateExtractor fileDateExtractor;

    public SyncServiceFile(FileDateExtractor fileDateExtractor){
        this.fileDateExtractor=fileDateExtractor;
    }

    public List<TransferPair> buildTransferPairs(FileFilter fileFilter){
        logger.debug("buildTransferPairs start");
        Config config=ApplicationContext.getInstance().getConfig();
        //we consider that config has only one folder at the moment
            List<TransferPair> pairs = new LinkedList();
            String[] srcFolders= config.getSourcePath().split(Config.PATH_DELIMITER);
            int i=0;
            for (String srcFolder:srcFolders) {
                logger.debug("working with source folder "+srcFolder,srcFolder);
                buildTransferFolderPairs(srcFolder,fileFilter, pairs,++i);
            }
        logger.debug("buildTransferPairs end");
        return pairs;
    }


    private void buildTransferFolderPairs(String sourceFolder, FileFilter fileFilter, List<TransferPair> pairs,int folderNum){
        logger.debug("buildTransferFolderPairs start");

        Config config = ApplicationContext.getInstance().getConfig();
        String destFolder=config.getDestPath();
        File srcFileFolder= new File(sourceFolder);
        if (!srcFileFolder.isDirectory()) throw new ApplicationException("Source folder is not a Directory "+sourceFolder);
        if (!srcFileFolder.canRead()) throw new PathNotReadyException("Can not read source folder"+sourceFolder);

        File destFileFolder= new File(destFolder);
        if (!destFileFolder.isDirectory()) throw new ApplicationException("Dest folder is not a Directory "+destFolder);
        if (!destFileFolder.canWrite()) throw new PathNotReadyException("Can not read dest folder"+destFolder);

        File[] srcFileList =srcFileFolder.listFiles(fileFilter);
        if (srcFileList!=null) {
            logger.debug("Files after filter " + srcFileList.length);
            if (srcFileList.length > 0) {
                for (File srcFile : srcFileList) {
                    File destFile = new File(getDestFolderName(srcFile, destFolder), srcFile.getName());
                    if (logger.isDebugEnabled())
                        logger.debug(String.format("File examined - source: %s; dest: %s", srcFile.getName(), destFile.getName()));
                    if (srcFile.isFile())
                        if (isCopyPair(srcFile, destFile)) {
                            //we have to add suffix, it prevents the case where 2 files have the same name from 2 folders
                            pairs.add(new FilePair(srcFile, new File(getFinalDestFileName(destFile,folderNum))));
                            if (logger.isDebugEnabled())
                                logger.debug("Pair added");
                        }
                    //implement recirsive logic here for subfolders
                }
            }
        }

        logger.debug("buildTransferFolderPairs end");
    }

    protected String getFinalDestFileName(File destFile, int folderNum){
        Pair p=stripExtension(destFile.getAbsolutePath());
        String fname=String.format("%s_%d.%s",p.fileName,folderNum,p.extention);
        logger.debug("Final dest file name:"+fname);
        return fname;
    }

    public static class Pair {String fileName,extention;

        public Pair(String fileName, String extention) {
            this.fileName = fileName;
            this.extention = extention;
        }
    }

    public static Pair stripExtension (String str) {
        if (str == null) return null;
        int pos = str.lastIndexOf(".");
        if (pos == -1) return new Pair(str,null);
        return new Pair(str.substring(0, pos),str.substring(pos+1));
    }


    private boolean isCopyPair(File srcFile,File destFile){
        if (srcFile==null) return false;
        if (destFile==null) return false;
        if (!srcFile.getName().equals(destFile.getName())) return false;
        if (srcFile.length()!=destFile.length()) return true;
        if (!destFile.exists())  return true;
        return false;
    }

    private String getDestFolderName(File srcFile, String destFolder){
        return String.format("%s/%s_01_cloud",destFolder,dateFormat.format(fileDateExtractor.get(srcFile)));

    }

}
