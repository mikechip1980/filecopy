package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;

import java.io.File;
import java.io.FileReader;

public class FileFilter implements java.io.FileFilter {
    Config config;
    public FileFilter(Config config){
        this.config=config;
    }

    @Override
    public boolean accept(File file) {
        if (!file.isFile()) return false;
        if (config.getLastSyncDate()!=null&&
                config.getLastSyncDate().getTime()>file.lastModified())
            return false;
        return true;
    }
}
