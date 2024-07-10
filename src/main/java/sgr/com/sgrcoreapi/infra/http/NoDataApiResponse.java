package sgr.com.sgrcoreapi.infra.http;

public record NoDataApiResponse(
        int status,
        String message,
        long timestamp
) {
    public NoDataApiResponse(int status, String message) {
        this(status, message, System.currentTimeMillis());
    }

    public NoDataApiResponse(int status) {
        this(status, null, System.currentTimeMillis());
    }
}
