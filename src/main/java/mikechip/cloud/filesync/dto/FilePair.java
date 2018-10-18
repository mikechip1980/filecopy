package mikechip.cloud.filesync.dto;

import java.io.File;

public class FilePair extends TransferPair {

    public FilePair(File sourceFile, File destFile) {
        super(sourceFile, destFile);
    }
}
