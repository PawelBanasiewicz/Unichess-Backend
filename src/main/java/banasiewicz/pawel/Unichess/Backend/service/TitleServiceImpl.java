package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.title.TitleResponseDto;
import banasiewicz.pawel.Unichess.Backend.exception.title.TitleError;
import banasiewicz.pawel.Unichess.Backend.exception.title.TitleException;
import banasiewicz.pawel.Unichess.Backend.model.Title;
import banasiewicz.pawel.Unichess.Backend.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;

    @Autowired
    public TitleServiceImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Override
    public List<TitleResponseDto> getTitles() {
        return titleRepository.findAll().stream()
                .map(TitleResponseDto::from)
                .toList();
    }

    @Override
    public TitleResponseDto getTitleResponseByAbbreviation(final String abbreviation) {
        final Title title = titleRepository.findByNameOrAbbreviationIgnoreCase(abbreviation)
                .orElseThrow(() -> new TitleException(TitleError.TITLE_NOT_FOUND, abbreviation));
        return TitleResponseDto.from(title);
    }
}
