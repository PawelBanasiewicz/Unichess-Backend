package banasiewicz.pawel.Unichess.Backend.exception;

import banasiewicz.pawel.Unichess.Backend.exception.player.PlayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.stream.Collectors;

import static banasiewicz.pawel.Unichess.Backend.exception.ErrorResponse.buildErrorResponse;

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

        final ErrorResponse errorResponse = ErrorResponse.buildErrorResponse("VALIDATION_ERROR", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(final HttpMessageNotReadableException e) {
        final String message = e.getMessage();
        final ErrorResponse errorResponse = ErrorResponse.buildErrorResponse("JSON_PARSE_ERROR", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlePlayerException(final PlayerException e, final Locale locale) {
        final String message = messageSource.getMessage(e.getPlayerError().getMessageKey(), e.getParams(), locale);
        final String errorCode = e.getPlayerError().name();
        final HttpStatus httpStatus = e.getPlayerError().getHttpStatus();

        final ErrorResponse errorResponse = buildErrorResponse(errorCode, message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
