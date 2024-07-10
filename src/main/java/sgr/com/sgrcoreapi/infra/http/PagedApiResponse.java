package sgr.com.sgrcoreapi.infra.http;

import org.springframework.data.domain.Page;

import java.util.List;

public record PagedApiResponse<T> (
        int status,
        String message,
        long timestamp,
        List<T> data,
        PaginationDetails pagination
) {
    public PagedApiResponse(int status, String message, Page<T> data) {
        this(
                status,
                message,
                System.currentTimeMillis(),
                data.getContent(),
                new PaginationDetails(data)
        );
    }

    public PagedApiResponse(int status, Page<T> data) {
        this(
                status,
                null,
                System.currentTimeMillis(),
                data.getContent(),
                new PaginationDetails(data)
        );
    }
}
