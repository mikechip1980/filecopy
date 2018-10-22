package mikechip.cloud.filesync.service;

import java.io.File;

public interface FolderNameExtractor {
    String build(File srcFile, String destFolder);
}
