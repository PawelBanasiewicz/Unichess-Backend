package banasiewicz.pawel.Unichess.Backend.response;

import java.time.LocalDateTime;

public record UnichessApiResponse<T>(String message, T data, LocalDateTime timestamp) {

    public UnichessApiResponse(String message, T data) {
        this(message, data, LocalDateTime.now());
    }

    public static <T> UnichessApiResponse<T> success(final String message, final T data) {
        return new UnichessApiResponse<>(message, data);
    }
}
