package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;

import java.io.File;

public class FolderNameExtractorLastUpdate implements FolderNameExtractor {

    @Override
    public String build(File srcFile, String destFolder) {
        return destFolder+"/"+Config.dateFormat.format(srcFile.lastModified());
    }
}
