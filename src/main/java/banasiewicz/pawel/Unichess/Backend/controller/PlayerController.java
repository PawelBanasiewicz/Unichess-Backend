package banasiewicz.pawel.Unichess.Backend.controller;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerPatchDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;
import banasiewicz.pawel.Unichess.Backend.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<PlayerResponseDto> getPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping("/{id}")
    public PlayerResponseDto getPlayerById(final @PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @PostMapping
    public PlayerResponseDto addPlayer(final @Valid @RequestBody PlayerCreateDto playerCreateDto) {
        return playerService.addPlayer(playerCreateDto);
    }

    @PutMapping("/{id}")
    public PlayerResponseDto putPlayer(final @PathVariable Long id, final @Valid @RequestBody PlayerCreateDto playerCreateDto) {
        return playerService.putPlayer(id, playerCreateDto);
    }

    @PatchMapping("/{id}")
    public PlayerResponseDto patchPlayer(final @PathVariable Long id, final @Valid @RequestBody PlayerPatchDto playerPatchDto) {
        return playerService.patchPlayer(id, playerPatchDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(final @PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
