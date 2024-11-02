package banasiewicz.pawel.Unichess.Backend.dto.player;

import banasiewicz.pawel.Unichess.Backend.model.Player;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record PlayerCreateDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @PastOrPresent LocalDate birthDate,
        Player.Sex sex,
        @NotBlank String nationality,
        @NotBlank String title,
        @Min(1) Integer eloRating
) {}

