package mikechip.cloud.filesync.service;

import java.io.File;
import java.io.IOException;

public interface Copyable {
    public void doCopy(File source, File dest) throws IOException;
}
