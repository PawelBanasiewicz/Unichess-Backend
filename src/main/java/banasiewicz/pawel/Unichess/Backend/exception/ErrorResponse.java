package banasiewicz.pawel.Unichess.Backend.exception;

import java.time.LocalDateTime;

record ErrorResponse(String errorCode,
                     String message,
                     LocalDateTime localDateTime) {

    public static ErrorResponse buildErrorResponse(final String errorCode, final String message) {
        return new ErrorResponse(errorCode, message, LocalDateTime.now());
    }
}
