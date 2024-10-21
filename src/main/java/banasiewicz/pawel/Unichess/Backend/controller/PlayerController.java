package banasiewicz.pawel.Unichess.Backend.controller;

import banasiewicz.pawel.Unichess.Backend.dto.PlayerDto;
import banasiewicz.pawel.Unichess.Backend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerDto> getPlayers() {
        return playerService.getPlayers();
    }
}
