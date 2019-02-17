package mikechip.cloud.filesync.dto;

import java.util.concurrent.LinkedBlockingQueue;

public class PairQueue extends LinkedBlockingQueue<TransferPair> {
    @Override
    public boolean add(TransferPair pair) {
        try {
             super.put(pair);
             return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
