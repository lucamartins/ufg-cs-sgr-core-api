package sgr.com.sgrcoreapi.infra.http;

public record ApiResponse<T>(
        int status,
        String message,
        long timestamp,
        T data
) {
    public ApiResponse(int status, String message, T data) {
        this(status, message, System.currentTimeMillis(), data);
    }

    public ApiResponse(int status, T data) {
        this(status, null, System.currentTimeMillis(), data);
    }
}
