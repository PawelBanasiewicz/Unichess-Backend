package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.PlayerDto;
import banasiewicz.pawel.Unichess.Backend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<PlayerDto> getPlayers() {
        return playerRepository.findAll().stream()
                .map(PlayerDto::from)
                .toList();
    }
}
