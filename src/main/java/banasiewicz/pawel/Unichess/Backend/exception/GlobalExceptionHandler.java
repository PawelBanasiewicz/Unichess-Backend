package banasiewicz.pawel.Unichess.Backend.exception;

import banasiewicz.pawel.Unichess.Backend.exception.player.PlayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static banasiewicz.pawel.Unichess.Backend.exception.ErrorResponse.buildErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
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
