package banasiewicz.pawel.Unichess.Backend.exception.title;

public class TitleException extends RuntimeException {

    private final TitleError titleError;
    private final Object[] params;

    public TitleException(TitleError titleError, Object... params) {
        this.titleError = titleError;
        this.params = params;
    }

    public TitleError getTitleError() {
        return titleError;
    }

    public Object[] getParams() {
        return params;
    }
}
