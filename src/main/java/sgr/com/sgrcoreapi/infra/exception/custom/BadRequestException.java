package sgr.com.sgrcoreapi.infra.exception.custom;

import lombok.Getter;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {
    private final List<String> errorMessages;

    public BadRequestException(List<String> errorMessages) {
        super();
        this.errorMessages = errorMessages;
    }

    public BadRequestException(String errorMessage) {
        super();
        this.errorMessages = List.of(errorMessage);
    }
}
