package mikechip.cloud.filesync.service;

import java.io.File;
import java.util.Date;

public interface FileDateExtractor {
    Date get(File srcFile);
}
