package banasiewicz.pawel.Unichess.Backend.controller;

import banasiewicz.pawel.Unichess.Backend.dto.title.TitleResponseDto;
import banasiewicz.pawel.Unichess.Backend.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitleController {

    private final TitleService titleService;

    @Autowired
    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping
    public List<TitleResponseDto> getTitles() {
        return titleService.getTitles();
    }

    @GetMapping("/{abbreviation}")
    public TitleResponseDto getTitleByAbbreviation(@PathVariable String abbreviation) {
        return titleService.getTitleResponseByAbbreviation(abbreviation);
    }
}
