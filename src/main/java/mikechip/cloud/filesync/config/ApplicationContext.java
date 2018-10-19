package mikechip.cloud.filesync.config;

import mikechip.cloud.filesync.service.*;


public class ApplicationContext {
    private static ApplicationContext appContext=new ApplicationContext();
    private ApplicationContext(){init();}
    private Config config;
    private SyncService syncService;

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
    }
}
