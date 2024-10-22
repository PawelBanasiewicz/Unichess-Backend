package banasiewicz.pawel.Unichess.Backend.dto.opening;

import banasiewicz.pawel.Unichess.Backend.model.Opening;
import jakarta.validation.constraints.NotNull;

public record OpeningResponseDto(
        Long id,
        String code,
        String name,
        String fen,
        String pgnMoves,
        String uciMoves
) {
    public static OpeningResponseDto from(final @NotNull Opening opening) {
        return new OpeningResponseDto(
                opening.getId(),
                opening.getCode(),
                opening.getName(),
                opening.getFen(),
                opening.getPgnMoves(),
                opening.getUciMoves()
        );
    }
}
