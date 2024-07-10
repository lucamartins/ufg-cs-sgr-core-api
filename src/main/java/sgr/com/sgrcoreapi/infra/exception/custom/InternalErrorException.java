package sgr.com.sgrcoreapi.infra.exception.custom;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException() {
        super();
    }

    public InternalErrorException(String message) {
        super(message);
    }
}
