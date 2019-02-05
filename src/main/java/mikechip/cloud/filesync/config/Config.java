package mikechip.cloud.filesync.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Config {

    private static  Config config;


    private static final Logger logger
            = LoggerFactory.getLogger(Config.class);

    private final String SRC_PATH_PARAM="files.source.path";
    private final String DEST_PATH_PARAM="files.destination.path";
    public final static String RUNNTIME_FOLDER_NAME="./runtime";
    public final static String LAST_SYNC_FILE_NAME=RUNNTIME_FOLDER_NAME+"/lastSyncDate";
    public  final static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public final static String PATH_DELIMITER =";";
    public final String PROPERTIES_FILE_NAME = "config.properties";
    private Properties prop;
    private String sourcePath,destPath;
    private Date lastSyncDate;

    private Config() throws IOException, ParseException {
        loadConfigProperties();
        sourcePath=prop.getProperty(SRC_PATH_PARAM);
        destPath=prop.getProperty(DEST_PATH_PARAM);
        validateConfig();
        initRuntimeEnv();
        lastSyncDate=readLastSyncDate();
    }

    public static void init() throws IOException, ParseException {
        config= new Config();
    }

    public static Config getInstance(){
        return config;
    }

    private void initRuntimeEnv() throws IOException {
        File runtimeFolder=new File(RUNNTIME_FOLDER_NAME);
        if (!runtimeFolder.exists()&&!runtimeFolder.mkdir()) throw new IOException("Can not create runtime folder");
        File lastSyncFile=new File(LAST_SYNC_FILE_NAME);
        if (!lastSyncFile.exists()) {
            lastSyncFile.createNewFile();
            resetLastSyncDate();
        }
    }

    private Date readLastSyncDate() throws IOException, ParseException {
        BufferedReader br=null; String line=null;
        try {
            br = new BufferedReader(new FileReader(LAST_SYNC_FILE_NAME));
            line = br.readLine();
        } finally {
            br.close();
        }
        return dateFormat.parse(line);
    }

    public void updateLastSyncDate() throws IOException {
        updateLastSyncDate(new Date());
    }

    public static void resetLastSyncDate() throws IOException {
        updateLastSyncDate(new Date(0));
    }

    private static void updateLastSyncDate(Date date) throws IOException {
        FileWriter fw=null;
        try {
            fw = new FileWriter(LAST_SYNC_FILE_NAME);
            fw.write(dateFormat.format(date));
            fw.flush();
        } finally {
            fw.close();
        }
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getDestPath() {
        return destPath;
    }

    public Date getLastSyncDate() {
        return lastSyncDate;
    }

    private void validateConfig(){
        if (getSourcePath()==null) throw new IllegalArgumentException(SRC_PATH_PARAM+" parameter should be specified");
        if (getDestPath()==null) throw new IllegalArgumentException(DEST_PATH_PARAM+" parameter should be specified");
        //if (getSourcePath().contains(PATH_DELIMITER)||getDestPath().contains(";")) throw new IllegalArgumentException("Multiple paths are not supported yet");
    }

    private InputStream getResourcePropertiesStream(){
        return getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
    }

    private InputStream getPropertiesStream(){
        InputStream stream;
        try {
             stream = new FileInputStream(new File("./config/"+PROPERTIES_FILE_NAME));
             logger.debug("external config file found");
        } catch (FileNotFoundException e) {
            stream= getResourcePropertiesStream();
            logger.debug("resource config file found");
        }
        return stream;
    }

    public void loadConfigProperties() throws IOException {
        InputStream inputStream=null;
        if (prop==null) prop=new Properties();
        try {
            inputStream=getPropertiesStream();
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + PROPERTIES_FILE_NAME + "' not found in the classpath");
            }
        } finally {
            if (inputStream!=null) inputStream.close();
        }
        }
}
