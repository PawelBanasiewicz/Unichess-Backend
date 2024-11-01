package banasiewicz.pawel.Unichess.Backend.exception.player;

public class PlayerException extends RuntimeException {

    private final PlayerError playerError;
    private final Object[] params;

    public PlayerException(PlayerError playerError, Object... params) {
        this.playerError = playerError;
        this.params = params;
    }

    public PlayerError getPlayerError() {
        return playerError;
    }

    public Object[] getParams() {
        return params;
    }
}
