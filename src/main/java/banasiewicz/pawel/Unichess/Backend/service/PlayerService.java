package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;

import java.util.List;

public interface PlayerService {

    List<PlayerResponseDto> getPlayers();

    PlayerResponseDto getPlayerById(final Long id);

    PlayerResponseDto addPlayer(final PlayerCreateDto playerCreateDto);

    void deletePlayer(final Long id);
}
