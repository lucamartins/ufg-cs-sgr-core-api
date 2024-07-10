package sgr.com.sgrcoreapi.infra.exception.custom;

import java.util.List;

public record CustomExceptionBody(
        Integer httpStatusCode,
        List<String> errorsMessages
) {
}
