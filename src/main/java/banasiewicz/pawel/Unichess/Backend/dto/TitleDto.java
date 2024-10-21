package banasiewicz.pawel.Unichess.Backend.dto;

import banasiewicz.pawel.Unichess.Backend.model.Title;
import jakarta.validation.constraints.NotNull;

public record TitleDto (
        Long id,
        String name,
        String abbreviation,
        Integer eloThreshold,
        Boolean requiresNorm,
        Boolean onlyFemale,
        Integer introductionYear
) {
    public static TitleDto from(final @NotNull Title title) {
        return new TitleDto(
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
