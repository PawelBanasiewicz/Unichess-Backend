package banasiewicz.pawel.Unichess.Backend.openings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/openings")
class OpeningController {

    private final OpeningService openingService;

    @Autowired
    public OpeningController(OpeningService openingService) {
        this.openingService = openingService;
    }

    @GetMapping
    public List<Opening> getOpenings() {
        return openingService.getOpenings();
    }
}
