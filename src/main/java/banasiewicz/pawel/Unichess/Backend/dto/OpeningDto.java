package banasiewicz.pawel.Unichess.Backend.dto;

import banasiewicz.pawel.Unichess.Backend.model.Opening;
import jakarta.validation.constraints.NotNull;

public record OpeningDto (
        Long id,
        String code,
        String name,
        String fen,
        String pgnMoves,
        String uciMoves
) {
    public static OpeningDto from(final @NotNull Opening opening) {
        return new OpeningDto(
                opening.getId(),
                opening.getCode(),
                opening.getName(),
                opening.getFen(),
                opening.getPgnMoves(),
                opening.getUciMoves()
        );
    }
}
