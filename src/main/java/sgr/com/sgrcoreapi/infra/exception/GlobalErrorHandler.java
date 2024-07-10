package sgr.com.sgrcoreapi.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sgr.com.sgrcoreapi.infra.exception.custom.BadRequestException;
import sgr.com.sgrcoreapi.infra.exception.custom.CustomExceptionBody;
import sgr.com.sgrcoreapi.infra.exception.custom.ForbiddenException;
import sgr.com.sgrcoreapi.infra.exception.custom.NotFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomExceptionBody> handleBadRequestException(BadRequestException exception) {
        var badRequestDetails = new CustomExceptionBody(
                HttpStatus.BAD_REQUEST.value(),
                exception.getErrorMessages()
        );

        return ResponseEntity.badRequest().body(badRequestDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionBody> handleValidationsExceptions(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getFieldErrors();

        var errorMessages = fieldErrors.stream()
                .map(fieldError -> {
                    var field = fieldError.getField();
                    var message = fieldError.getDefaultMessage();

                    return String.format("%s: %s", field, message);
                })
                .toList();

        var badRequestDetails = new CustomExceptionBody(
                HttpStatus.BAD_REQUEST.value(),
                errorMessages
        );

        return ResponseEntity.badRequest().body(badRequestDetails);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<CustomExceptionBody> handleValidationsExceptions() {
        var badRequestDetails = new CustomExceptionBody(
                HttpStatus.BAD_REQUEST.value(),
                List.of("Invalid request. Check the URL, body fields and try again.")
        );

        return ResponseEntity.badRequest().body(badRequestDetails);
    }

    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomExceptionBody> handleForbiddenException(ForbiddenException exception) {
        var customExceptionBody = new CustomExceptionBody(
                HttpStatus.FORBIDDEN.value(),
                List.of(exception.getMessage())
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customExceptionBody);
    }
}
