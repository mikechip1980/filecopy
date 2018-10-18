package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;

import java.io.*;

public class FileServiceIO implements Copyable {
    @Override


    public void doCopy(File source, File dest) throws IOException{
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[10000];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            if (is!=null) is.close();
            if (os!=null) os.close();
        }

    }



}
