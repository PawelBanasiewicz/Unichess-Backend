package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerPatchDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;

import java.util.List;

public interface PlayerService {

    List<PlayerResponseDto> getPlayers();

    PlayerResponseDto getPlayerById(final Long id);

    PlayerResponseDto addPlayer(final PlayerCreateDto playerCreateDto);

    PlayerResponseDto putPlayer(final Long id, final PlayerCreateDto playerCreateDto);

    PlayerResponseDto patchPlayer(final Long id, final PlayerPatchDto playerPatchDto);

    void deletePlayer(final Long id);
}
