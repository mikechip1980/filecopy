package mikechip.cloud.filesync.config;

import mikechip.cloud.filesync.service.*;

import java.util.Date;


public class ApplicationContext {
    private static ApplicationContext appContext=new ApplicationContext();
    private ApplicationContext(){init();}
    private Config config;



    private SyncService syncService;
    private FileDateExtractor fileDateExtractor;

    public Config getConfig() {
        return config;
    }

    public SyncService getSyncService() {
        return syncService;
    }

    public FileService getFileService() {
        return fileService;
    }

    public SyncExecuteService getSyncExecuteService() {
        return syncExecuteService;
    }

    public FileDateExtractor getFileDateExtractor() {
        return fileDateExtractor;
    }

    private FileService fileService;
    private SyncExecuteService syncExecuteService;
    public static ApplicationContext getInstance(){
        return appContext;
    }

    private void init() {
        try {
            Config.init();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        config =Config.getInstance();
       // fileDateExtractor=(file)->new Date(file.lastModified());
        fileDateExtractor=new FolderNameExtractorMeta();
        syncService=new SyncServiceFile(fileDateExtractor);
        fileService = new FileService(new FileServiceNIO());
        syncExecuteService=new SyncExecuteServiceFile();


    }
}
