package banasiewicz.pawel.Unichess.Backend.controller;

import banasiewicz.pawel.Unichess.Backend.dto.title.TitleResponseDto;
import banasiewicz.pawel.Unichess.Backend.response.UnichessApiResponse;
import banasiewicz.pawel.Unichess.Backend.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitleController {

    private final TitleService titleService;
    private final MessageSource messageSource;

    @Autowired
    public TitleController(TitleService titleService, MessageSource messageSource) {
        this.titleService = titleService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<UnichessApiResponse<List<TitleResponseDto>>> getTitles() {
        final List<TitleResponseDto> titles = titleService.getTitles();
        final String message = messageSource.getMessage("success.title.get.all", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<List<TitleResponseDto>> response = UnichessApiResponse.success(message, titles);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{title}")
    public ResponseEntity<UnichessApiResponse<TitleResponseDto>> getTitleByFullNameOrAbbreviation(@PathVariable final String title) {
        final TitleResponseDto titleResponseDto = titleService.getTitleByFullNameOrAbbreviation(title);
        final String message = messageSource.getMessage("success.title.get", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<TitleResponseDto> response = UnichessApiResponse.success(message, titleResponseDto);
        return ResponseEntity.ok(response);
    }
}
