package banasiewicz.pawel.Unichess.Backend.exception;

public class DomainException extends RuntimeException {

    private final ErrorType errorType;
    private final Object[] params;

    public DomainException(ErrorType errorType, Object... params) {
        this.errorType = errorType;
        this.params = params;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public Object[] getParams() {
        return params;
    }
}
