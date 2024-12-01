package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerPatchDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;
import banasiewicz.pawel.Unichess.Backend.exception.DomainException;
import banasiewicz.pawel.Unichess.Backend.exception.ErrorType;
import banasiewicz.pawel.Unichess.Backend.model.Player;
import banasiewicz.pawel.Unichess.Backend.model.Title;
import banasiewicz.pawel.Unichess.Backend.repository.PlayerRepository;
import banasiewicz.pawel.Unichess.Backend.repository.TitleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        return playerRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(PlayerResponseDto::from)
                .toList();
    }

    @Override
    public PlayerResponseDto getPlayerById(final Long id) {
        final Player player = playerRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.PLAYER_NOT_FOUND, id));
        return PlayerResponseDto.from(player);
    }

    @Override
    @Transactional
    public PlayerResponseDto addPlayer(final PlayerCreateDto playerCreateDto) {
        checkPlayerExistence(playerCreateDto.firstName(), playerCreateDto.lastName(), playerCreateDto.birthDate());
        final Title title = loadPlayerTitle(playerCreateDto.title());

        final Player playerToSave = new Player(
                playerCreateDto.firstName(),
                playerCreateDto.lastName(),
                playerCreateDto.birthDate(),
                playerCreateDto.sex(),
                playerCreateDto.nationality(),
                title,
                playerCreateDto.eloRating()
        );

        final Player savedPlayer = playerRepository.save(playerToSave);
        return PlayerResponseDto.from(savedPlayer);
    }

    @Override
    @Transactional
    public PlayerResponseDto putPlayer(final Long id, final PlayerCreateDto playerCreateDto) {
       checkPlayerExistenceExcludingId(id, playerCreateDto.firstName(), playerCreateDto.lastName(), playerCreateDto.birthDate());
       final Title title = loadPlayerTitle(playerCreateDto.title());

       return playerRepository.findById(id)
                .map(playerFromDatabase -> {
                    playerFromDatabase.setFirstName(playerCreateDto.firstName());
                    playerFromDatabase.setLastName(playerCreateDto.lastName());
                    playerFromDatabase.setBirthDate(playerCreateDto.birthDate());
                    playerFromDatabase.setSex(playerCreateDto.sex());
                    playerFromDatabase.setNationality(playerCreateDto.nationality());
                    playerFromDatabase.setTitle(title);
                    playerFromDatabase.setEloRating(playerCreateDto.eloRating());

                    final Player savedPlayer = playerRepository.save(playerFromDatabase);
                    return PlayerResponseDto.from(savedPlayer);
                }).orElseThrow(() -> new DomainException(ErrorType.PLAYER_NOT_FOUND, id));
    }

    @Override
    @Transactional
    public PlayerResponseDto patchPlayer(final Long id, final PlayerPatchDto playerPatchDto) {
        checkPlayerExistenceExcludingId(id, playerPatchDto.firstName(),
                playerPatchDto.lastName(), playerPatchDto.birthDate());
        final Title title = loadPlayerTitle(playerPatchDto.title());

        return playerRepository.findById(id)
                .map(playerFromDatabase -> {
                    if (StringUtils.hasText(playerPatchDto.firstName())) {
                        playerFromDatabase.setFirstName(playerPatchDto.firstName());
                    }

                    if (StringUtils.hasText(playerPatchDto.lastName())) {
                        playerFromDatabase.setLastName(playerPatchDto.lastName());
                    }

                    if (playerPatchDto.birthDate() != null) {
                        playerFromDatabase.setBirthDate(playerPatchDto.birthDate());
                    }

                    if (playerPatchDto.sex() != null) {
                        playerFromDatabase.setSex(playerPatchDto.sex());
                    }

                    if (StringUtils.hasText(playerPatchDto.nationality())) {
                        playerFromDatabase.setNationality(playerPatchDto.nationality());
                    }

                    if (title != null) {
                        playerFromDatabase.setTitle(title);
                    }

                    if (playerPatchDto.eloRating() != null) {
                        playerFromDatabase.setEloRating(playerPatchDto.eloRating());
                    }

                    playerRepository.save(playerFromDatabase);
                    return PlayerResponseDto.from(playerFromDatabase);

                }).orElseThrow(() -> new DomainException(ErrorType.PLAYER_NOT_FOUND, id));
    }

    @Override
    @Transactional
    public void deletePlayer(final Long id) {
        playerRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.PLAYER_NOT_FOUND, id));
        playerRepository.deleteById(id);
    }

    private void checkPlayerExistence(final String firstName, final String lastName, final LocalDate birthDate) {
        final boolean playerAlreadyExists = playerRepository.existsByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);

        if (playerAlreadyExists) {
            throw new DomainException(ErrorType.PLAYER_ALREADY_EXIST, firstName, lastName, birthDate);
        }
    }

    private void checkPlayerExistenceExcludingId(final Long id, final String firstName, final String lastName, final LocalDate birthDate) {
        final boolean playerAlreadyExists = playerRepository.existsByFirstNameAndLastNameAndBirthDateExcludingId(id, firstName, lastName, birthDate);

        if (playerAlreadyExists) {
            throw new DomainException(ErrorType.PLAYER_ALREADY_EXIST, firstName, lastName, birthDate);
        }
    }

    private Title loadPlayerTitle(final String title) {
        if (title == null) {
            return null;
        }

        return titleRepository.findByFullNameOrAbbreviationIgnoreCase(title)
                .orElseThrow(() -> new DomainException(ErrorType.TITLE_NOT_FOUND, title));
    }
}
