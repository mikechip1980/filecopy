package mikechip.cloud.filesync.config;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String s) {
        super(s);
    }
    public ApplicationException(Throwable e) {
        super(e);
    }
}
