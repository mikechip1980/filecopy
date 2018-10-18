package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.config.Config;
import mikechip.cloud.filesync.dto.TransferPair;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class SyncServiceFileTest {

    @Test
    public void buildTransferPairsTest(){
        SyncService srv= new SyncServiceFile();
        List<TransferPair> pairs =srv.buildTransferPairs((file)->true);

        System.out.println("Pairs count:"+pairs.size());
        for (TransferPair pair: pairs)
            System.out.println(pair.getSourceName()+":"+pair.getDestName());
    }
}
