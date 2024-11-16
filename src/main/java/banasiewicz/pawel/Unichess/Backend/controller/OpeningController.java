package banasiewicz.pawel.Unichess.Backend.controller;


import banasiewicz.pawel.Unichess.Backend.dto.opening.OpeningResponseDto;
import banasiewicz.pawel.Unichess.Backend.response.UnichessApiResponse;
import banasiewicz.pawel.Unichess.Backend.service.OpeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/openings")
public class OpeningController {

    private final OpeningService openingService;
    private final MessageSource messageSource;

    @Autowired
    public OpeningController(OpeningService openingService, MessageSource messageSource) {
        this.openingService = openingService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<UnichessApiResponse<List<OpeningResponseDto>>> getOpenings() {
        final List<OpeningResponseDto> openings = openingService.getOpenings();
        final String message = messageSource.getMessage("success.opening.get.all", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<List<OpeningResponseDto>> response = UnichessApiResponse.success(message, openings);
        return ResponseEntity.ok(response);
    }
}
