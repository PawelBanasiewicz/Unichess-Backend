package banasiewicz.pawel.Unichess.Backend.dto.player;

import banasiewicz.pawel.Unichess.Backend.model.Player;
import banasiewicz.pawel.Unichess.Backend.validation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record PlayerCreateDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull @PastOrPresent @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
        Player.Sex sex,
        @NotBlankOrNull String nationality,
        @NotBlankOrNull String title,
        @Min(1) Integer eloRating
) {}

