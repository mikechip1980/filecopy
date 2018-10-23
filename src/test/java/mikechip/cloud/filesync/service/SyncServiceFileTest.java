package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.dto.TransferPair;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class SyncServiceFileTest {

    @Test
    public void buildTransferPairsLastModifiedTest(){
        SyncService srv= new SyncServiceFile((file)->new Date(file.lastModified()));
        List<TransferPair> pairs =srv.buildTransferPairs((file)->true);

        System.out.println("Pairs count:"+pairs.size());
        for (TransferPair pair: pairs)
            System.out.println(pair.getSourceName()+":"+pair.getDestName());
    }

    @Test
    public void buildTransferPairsMetaTest(){
        SyncService srv= new SyncServiceFile(new FolderNameExtractorMeta());
        List<TransferPair> pairs =srv.buildTransferPairs((file)->true);

        System.out.println("Pairs count:"+pairs.size());
        for (TransferPair pair: pairs)
            System.out.println(pair.getSourceName()+":"+pair.getDestName());
    }
}
