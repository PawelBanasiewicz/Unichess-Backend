package banasiewicz.pawel.Unichess.Backend.exception.title;

import org.springframework.http.HttpStatus;

public enum TitleError {
    TITLE_NOT_FOUND("title.not.found", HttpStatus.NOT_FOUND);

    private final String messageKey;
    private final HttpStatus httpStatus;

    TitleError(String messageKey, HttpStatus httpStatus) {
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
