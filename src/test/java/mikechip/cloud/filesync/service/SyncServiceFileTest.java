package mikechip.cloud.filesync.service;

import mikechip.cloud.filesync.dto.TransferPair;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SyncServiceFileTest  {
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

    @Test
    public void stripFileNameTest(){
        SyncServiceFile.Pair p= SyncServiceFile.stripExtension("\\home\\file.txt");
        assertEquals(p.fileName,"\\home\\file");
        assertEquals(p.extention,"txt");
    }

    @Test
    public void stripFileNameTestNoExt(){
        SyncServiceFile.Pair p= SyncServiceFile.stripExtension("\\home\\file");
        assertEquals(p.fileName,"\\home\\file");
        assertNull(p.extention);
    }

    @Test
    public void stripFileNameTestMultyExt(){
        SyncServiceFile.Pair p= SyncServiceFile.stripExtension("\\home\\file.txt.txt");
        assertEquals(p.fileName,"\\home\\file.txt");
        assertEquals(p.extention,"txt");
    }
}
