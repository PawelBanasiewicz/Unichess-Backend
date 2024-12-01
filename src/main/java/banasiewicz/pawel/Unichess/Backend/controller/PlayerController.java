package banasiewicz.pawel.Unichess.Backend.controller;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerPatchDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;
import banasiewicz.pawel.Unichess.Backend.exception.ErrorResponse;
import banasiewicz.pawel.Unichess.Backend.response.UnichessApiResponse;
import banasiewicz.pawel.Unichess.Backend.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all players")
    @ApiResponse(responseCode = "200")
    @GetMapping
    public ResponseEntity<UnichessApiResponse<List<PlayerResponseDto>>> getPlayers() {
        final List<PlayerResponseDto> players = playerService.getPlayers();
        final String message = messageSource.getMessage("success.player.getPlayers", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<List<PlayerResponseDto>> response = UnichessApiResponse.success(message, players);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get player by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UnichessApiResponse<PlayerResponseDto>> getPlayerById(
            @Parameter(description = "ID of the player to be retrieved", example = "1")
            @PathVariable final Long id) {
        final PlayerResponseDto playerResponseDto = playerService.getPlayerById(id);
        final String message = messageSource.getMessage("success.player.getPlayerById", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<PlayerResponseDto> response = UnichessApiResponse.success(message, playerResponseDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add player", description = "Two players with the same firstName, lastName, birthday are not allowed")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<UnichessApiResponse<PlayerResponseDto>> addPlayer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the player to be created",
                    required = true
            )
            @Valid @RequestBody final PlayerCreateDto playerCreateDto) {
        final PlayerResponseDto playerResponseDto = playerService.addPlayer(playerCreateDto);
        final String message = messageSource.getMessage("success.player.addPlayer", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<PlayerResponseDto> response = UnichessApiResponse.success(message, playerResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Put player", description = "Edits the entire selected player. It does not add a new one if the ID does not exist")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UnichessApiResponse<PlayerResponseDto>> putPlayer(
            @Parameter(description = "ID of the player to be updated", example = "1")
            @PathVariable final Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update details of the player",
                    required = true
            )
            @Valid @RequestBody final PlayerCreateDto playerCreateDto) {
        final PlayerResponseDto playerResponseDto = playerService.putPlayer(id, playerCreateDto);
        final String message = messageSource.getMessage("success.player.putPlayer", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<PlayerResponseDto> response = UnichessApiResponse.success(message, playerResponseDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Patch player")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UnichessApiResponse<PlayerResponseDto>> patchPlayer(
            @Parameter(description = "ID of the player to be patched", example = "1")
            @PathVariable final Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Fields to be updated for the player",
                    required = true
            )
            @Valid @RequestBody final PlayerPatchDto playerPatchDto) {
        final PlayerResponseDto playerResponseDto = playerService.patchPlayer(id, playerPatchDto);
        final String message = messageSource.getMessage("success.player.patchPlayer", null, LocaleContextHolder.getLocale());
        final UnichessApiResponse<PlayerResponseDto> response = UnichessApiResponse.success(message, playerResponseDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete player")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<UnichessApiResponse<Void>> deletePlayer(
            @Parameter(description = "ID of the player to be deleted", example = "1")
            @PathVariable final Long id) {
        playerService.deletePlayer(id);
        final String message = messageSource.getMessage("success.player.deletePlayer", null, LocaleContextHolder.getLocale());
        UnichessApiResponse<Void> response = UnichessApiResponse.success(message, null);
        return ResponseEntity.ok(response);
    }
}
