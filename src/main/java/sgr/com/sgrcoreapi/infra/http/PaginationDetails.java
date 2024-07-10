package sgr.com.sgrcoreapi.infra.http;

import org.springframework.data.domain.Page;

public record PaginationDetails(
        int page,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean firstPage,
        boolean lastPage,
        boolean emptyPage
) {
    public <T> PaginationDetails(Page<T> data) {
        this(
                data.getNumber(),
                data.getSize(),
                data.getTotalPages(),
                data.getTotalElements(),
                data.isFirst(),
                data.isLast(),
                data.isEmpty()
        );
    }
}
