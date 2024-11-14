package banasiewicz.pawel.Unichess.Backend.response;

import java.time.LocalDateTime;

public record ApiResponse<T>(String message, T data, LocalDateTime timestamp) {

    public ApiResponse(String message, T data) {
        this(message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> success(final String message, final T data) {
        return new ApiResponse<>(message, data);
    }
}
