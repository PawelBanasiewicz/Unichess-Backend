package banasiewicz.pawel.Unichess.Backend.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidationException(final MethodArgumentNotValidException e) {
        final String message = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        final ErrorResponse errorResponse = ErrorResponse.build("VALIDATION_ERROR", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(final HttpMessageNotReadableException e) {
        final String message = e.getMessage();
        final ErrorResponse errorResponse = ErrorResponse.build("JSON_PARSE_ERROR", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDomainException(final DomainException e) {
        final String message = messageSource.getMessage(e.getErrorType().getMessageKey(), e.getParams(), LocaleContextHolder.getLocale());
        final String errorCode = e.getErrorType().name();
        final HttpStatus httpStatus = e.getErrorType().getHttpStatus();

        final ErrorResponse errorResponse = ErrorResponse.build(errorCode, message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
