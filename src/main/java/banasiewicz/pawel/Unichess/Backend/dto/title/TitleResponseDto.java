package banasiewicz.pawel.Unichess.Backend.dto.title;

import banasiewicz.pawel.Unichess.Backend.model.Title;
import jakarta.validation.constraints.NotNull;

public record TitleResponseDto(
        Long id,
        String name,
        String abbreviation,
        Integer eloThreshold,
        Boolean requiresNorm,
        Boolean onlyFemale,
        Integer introductionYear
) {
    public static TitleResponseDto from(final @NotNull Title title) {
        return new TitleResponseDto(
                title.getId(),
                title.getName(),
                title.getAbbreviation(),
                title.getEloThreshold(),
                title.getRequiresNorm(),
                title.getOnlyFemale(),
                title.getIntroductionYear()
        );
    }
}
