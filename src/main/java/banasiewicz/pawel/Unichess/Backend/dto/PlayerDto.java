package banasiewicz.pawel.Unichess.Backend.dto;

import banasiewicz.pawel.Unichess.Backend.model.Player;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PlayerDto(Long id,
                        String firstName,
                        String lastName,
                        LocalDate birthDate,
                        Player.Sex sex,
                        String nationality,
                        String title,
                        Integer eloRating) {

    public static PlayerDto from(final @NotNull Player player) {
        return new PlayerDto(player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getBirthDate(),
                player.getSex(),
                player.getNationality(),
                player.getTitle() != null ? player.getTitle().getAbbreviation() : null,
                player.getEloRating());
    }
}
