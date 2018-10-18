package mikechip.cloud.filesync.dto;

import java.io.File;

public class TransferPair {
    private String sourceName, destName;
    private File sourceFile,destFile;

    public String getSourceName() {
        return sourceName;
    }

    public String getDestName() {
        return destName;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public File getDestFile() {
        return destFile;
    }

    public TransferPair(File sourceFile, File destFile) {
        this.sourceFile = sourceFile;
        this.destFile = destFile;
        sourceName = sourceFile.getAbsolutePath();
        destName= destFile.getAbsolutePath();
    }


}
