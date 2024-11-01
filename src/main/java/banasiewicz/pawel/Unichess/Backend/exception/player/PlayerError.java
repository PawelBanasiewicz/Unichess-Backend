package banasiewicz.pawel.Unichess.Backend.exception.player;

import org.springframework.http.HttpStatus;

public enum PlayerError {
    PLAYER_NOT_FOUND("player.not.found", HttpStatus.NOT_FOUND);

    private final String messageKey;
    private final HttpStatus httpStatus;

    PlayerError(String messageKey, HttpStatus httpStatus) {
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
