package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.TransferPair;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class SyncServiceFileTest {
    Config config;

    public SyncServiceFileTest() throws IOException, ParseException {
        Config.init();
        config=Config.getInstance();
    }

    @Test
    public void buildTransferPairsTest(){
        SyncService srv= new SyncServiceFile();
        List<TransferPair> pairs =srv.buildTransferPairs(config,(file)->true);

        System.out.println("Pairs count:"+pairs.size());
        for (TransferPair pair: pairs)
            System.out.println(pair.getSourceName()+":"+pair.getDestName());
    }
}
