package banasiewicz.pawel.Unichess.Backend.exception;

import java.time.LocalDateTime;

record ErrorResponse(String errorCode,
                     String message,
                     LocalDateTime timestamp) {

    public static ErrorResponse build(final String errorCode, final String message) {
        return new ErrorResponse(errorCode, message, LocalDateTime.now());
    }
}
