package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;
import banasiewicz.pawel.Unichess.Backend.exception.player.PlayerError;
import banasiewicz.pawel.Unichess.Backend.exception.player.PlayerException;
import banasiewicz.pawel.Unichess.Backend.model.Player;
import banasiewicz.pawel.Unichess.Backend.model.Title;
import banasiewicz.pawel.Unichess.Backend.repository.PlayerRepository;
import banasiewicz.pawel.Unichess.Backend.repository.TitleRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TitleRepository titleRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             TitleRepository titleRepository) {
        this.playerRepository = playerRepository;
        this.titleRepository = titleRepository;
    }

    @Override
    public List<PlayerResponseDto> getPlayers() {
        return playerRepository.findAll().stream()
                .map(PlayerResponseDto::from)
                .toList();
    }

    @Override
    public PlayerResponseDto getPlayerById(final Long id) {
        final Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerException(PlayerError.PLAYER_NOT_FOUND, id));
        return PlayerResponseDto.from(player);
    }

    @Override
    public PlayerResponseDto addPlayer(final PlayerCreateDto playerCreateDto) {

        checkPlayerExistence(playerCreateDto.firstName(), playerCreateDto.lastName(), playerCreateDto.birthDate());

        final Title title = titleRepository.findByAbbreviationIgnoreCase(playerCreateDto.title())
                .orElseThrow(() -> new EntityExistsException("Title not found with abbreviation: " + playerCreateDto.title()));

        Player player = new Player();
        player.setFirstName(playerCreateDto.firstName());
        player.setLastName(playerCreateDto.lastName());
        player.setBirthDate(playerCreateDto.birthDate());
        player.setSex(playerCreateDto.sex());
        player.setNationality(playerCreateDto.nationality());
        player.setTitle(title);
        player.setEloRating(playerCreateDto.eloRating());

        final Player savedPlayer = playerRepository.save(player);
        return PlayerResponseDto.from(savedPlayer);
    }

    private void checkPlayerExistence(final String firstName, final String lastName, final LocalDate birthDate) {
        final boolean playerAlreadyExists = playerRepository.existsByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);

        if (playerAlreadyExists) {
            throw new PlayerException(PlayerError.PLAYER_ALREADY_EXIST, firstName, lastName, birthDate);
        }
    }
}
