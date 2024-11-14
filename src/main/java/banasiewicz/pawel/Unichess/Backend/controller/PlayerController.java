package banasiewicz.pawel.Unichess.Backend.controller;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerPatchDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;
import banasiewicz.pawel.Unichess.Backend.response.ApiResponse;
import banasiewicz.pawel.Unichess.Backend.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final MessageSource messageSource;

    @Autowired
    public PlayerController(PlayerService playerService, MessageSource messageSource) {
        this.playerService = playerService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlayerResponseDto>>> getPlayers() {
        final List<PlayerResponseDto> players = playerService.getPlayers();
        final String message = messageSource.getMessage("success.player.get.all", null, LocaleContextHolder.getLocale());
        final ApiResponse<List<PlayerResponseDto>> response = ApiResponse.success(message, players);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlayerResponseDto>> getPlayerById(@PathVariable final Long id) {
        final PlayerResponseDto playerResponseDto = playerService.getPlayerById(id);
        final String message = messageSource.getMessage("success.player.get", null, LocaleContextHolder.getLocale());
        final ApiResponse<PlayerResponseDto> response = ApiResponse.success(message, playerResponseDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PlayerResponseDto>> addPlayer(@Valid @RequestBody final PlayerCreateDto playerCreateDto) {
        final PlayerResponseDto playerResponseDto = playerService.addPlayer(playerCreateDto);
        final String message = messageSource.getMessage("success.player.post", null, LocaleContextHolder.getLocale());
        final ApiResponse<PlayerResponseDto> response = ApiResponse.success(message, playerResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PlayerResponseDto>> putPlayer(@PathVariable final Long id, @Valid @RequestBody final PlayerCreateDto playerCreateDto) {
        final PlayerResponseDto playerResponseDto = playerService.putPlayer(id, playerCreateDto);
        final String message = messageSource.getMessage("success.player.put", null, LocaleContextHolder.getLocale());
        final ApiResponse<PlayerResponseDto> response = ApiResponse.success(message, playerResponseDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PlayerResponseDto>> patchPlayer(@PathVariable final Long id, @Valid @RequestBody final PlayerPatchDto playerPatchDto) {
        final PlayerResponseDto playerResponseDto = playerService.patchPlayer(id, playerPatchDto);
        final String message = messageSource.getMessage("success.player.patch", null, LocaleContextHolder.getLocale());
        final ApiResponse<PlayerResponseDto> response = ApiResponse.success(message, playerResponseDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePlayer(@PathVariable final Long id) {
        playerService.deletePlayer(id);
        final String message = messageSource.getMessage("success.player.delete", null, LocaleContextHolder.getLocale());
        ApiResponse<Void> response = ApiResponse.success(message, null);
        return ResponseEntity.ok(response);
    }
}
