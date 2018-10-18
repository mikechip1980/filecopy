package mikechip.cloud.filesync.service;

import java.io.File;
import java.io.IOException;



public class FileService {
    public FileService(Copyable copyable) {
        this.copyable=copyable;
    }
    Copyable copyable;
    public void copyFile(String sourceFileName, String destFileName) throws IOException {
            File srcFile=new File(sourceFileName);
            File destFile=new File(destFileName);
            copyFile(srcFile,destFile);
    }


     public void copyFile(File srcFile, File destFile) throws IOException {
         if (!srcFile.exists()) throw new IOException("Source file does not exist "+srcFile.getCanonicalPath());
         if (!srcFile.isFile()) throw new IOException("Source is a directory");
         if (!srcFile.canRead()) throw new SecurityException("Can't read source file "+srcFile.getCanonicalPath());
         if (!srcFile.canRead()) throw new SecurityException("Can't read source file "+srcFile.getCanonicalPath());

         destFile.getParentFile().mkdirs();// throw new SecurityException("Can't create folders for destination file "+destFile.getCanonicalPath());
//         if (!destFile.canWrite()) throw new SecurityException("Can't write to destination file "+destFile.getCanonicalPath());
         copyable.doCopy(srcFile,destFile);
     }

}
