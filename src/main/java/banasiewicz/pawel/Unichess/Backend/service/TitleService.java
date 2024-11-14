package banasiewicz.pawel.Unichess.Backend.service;


import banasiewicz.pawel.Unichess.Backend.dto.title.TitleResponseDto;

import java.util.List;

public interface TitleService {

    List<TitleResponseDto> getTitles();

    TitleResponseDto getTitleByFullNameOrAbbreviation(final String abbreviation);
}
