package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.PlayerDto;

import java.util.List;

public interface PlayerService {

    List<PlayerDto> getPlayers();
}
