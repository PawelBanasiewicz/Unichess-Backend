package banasiewicz.pawel.Unichess.Backend.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    TITLE_NOT_FOUND("title.not.found", HttpStatus.NOT_FOUND),
    PLAYER_NOT_FOUND("player.not.found", HttpStatus.NOT_FOUND),
    PLAYER_ALREADY_EXIST("player.already.exist", HttpStatus.CONFLICT);

    private final String messageKey;
    private final HttpStatus httpStatus;

    ErrorType(String messageKey, HttpStatus httpStatus) {
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
