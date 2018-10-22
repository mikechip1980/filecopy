package mikechip.cloud.filesync.config;

import mikechip.cloud.filesync.service.*;


public class ApplicationContext {
    private static ApplicationContext appContext=new ApplicationContext();
    private ApplicationContext(){init();}
    private Config config;



    private SyncService syncService;
    private FolderNameExtractor folderNameExtractor;

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

    public FolderNameExtractor getFolderNameExtractor() {
        return folderNameExtractor;
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
        syncService=new SyncServiceFile();
        fileService = new FileService(new FileServiceNIO());
        syncExecuteService=new SyncExecuteServiceFile();
        folderNameExtractor=new FolderNameExtractorLastUpdate();

    }
}
