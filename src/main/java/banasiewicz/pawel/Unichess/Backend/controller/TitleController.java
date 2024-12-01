package banasiewicz.pawel.Unichess.Backend.controller;

import banasiewicz.pawel.Unichess.Backend.dto.title.TitleResponseDto;
import banasiewicz.pawel.Unichess.Backend.exception.ErrorResponse;
import banasiewicz.pawel.Unichess.Backend.response.UnichessApiResponse;
import banasiewicz.pawel.Unichess.Backend.service.TitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all titles")
    @ApiResponse(responseCode = "200")
    @GetMapping
    public ResponseEntity<UnichessApiResponse<List<TitleResponseDto>>> getTitles() {
        final List<TitleResponseDto> titles = titleService.getTitles();
        final String message = messageSource.getMessage("success.title.getTitles", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<List<TitleResponseDto>> response = UnichessApiResponse.success(message, titles);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get title by name or abbreviation")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{title}")
    public ResponseEntity<UnichessApiResponse<TitleResponseDto>> getTitleByFullNameOrAbbreviation(
            @Parameter(description = "Name of abbreviation of title", example = "GM")
            @PathVariable final String title) {
        final TitleResponseDto titleResponseDto = titleService.getTitleByFullNameOrAbbreviation(title);
        final String message = messageSource.getMessage("success.title.getTitleByFullNameOrAbbreviation", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<TitleResponseDto> response = UnichessApiResponse.success(message, titleResponseDto);
        return ResponseEntity.ok(response);
    }
}
