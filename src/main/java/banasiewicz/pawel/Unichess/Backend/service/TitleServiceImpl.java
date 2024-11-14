package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.title.TitleResponseDto;
import banasiewicz.pawel.Unichess.Backend.exception.DomainException;
import banasiewicz.pawel.Unichess.Backend.exception.ErrorType;
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
    public TitleResponseDto getTitleByFullNameOrAbbreviation(final String title) {
        final Title foundTitle = titleRepository.findByFullNameOrAbbreviationIgnoreCase(title)
                .orElseThrow(() -> new DomainException(ErrorType.TITLE_NOT_FOUND, title));
        return TitleResponseDto.from(foundTitle);
    }
}
