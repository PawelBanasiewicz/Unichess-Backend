package banasiewicz.pawel.Unichess.Backend.controller;


import banasiewicz.pawel.Unichess.Backend.dto.OpeningDto;
import banasiewicz.pawel.Unichess.Backend.service.OpeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/openings")
public class OpeningController {

    private final OpeningService openingService;

    @Autowired
    public OpeningController(OpeningService openingService) {
        this.openingService = openingService;
    }

    @GetMapping
    public List<OpeningDto> getOpenings() {
        return openingService.getOpenings();
    }
}
