package banasiewicz.pawel.Unichess.Backend.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Error response schema")
public record ErrorResponse(String errorCode,
                     String message,
                     LocalDateTime timestamp) {

    public static ErrorResponse build(final String errorCode, final String message) {
        return new ErrorResponse(errorCode, message, LocalDateTime.now());
    }
}
