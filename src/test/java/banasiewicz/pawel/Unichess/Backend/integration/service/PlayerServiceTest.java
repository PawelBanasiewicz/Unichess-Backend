package banasiewicz.pawel.Unichess.Backend.integration.service;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerPatchDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;
import banasiewicz.pawel.Unichess.Backend.exception.DomainException;
import banasiewicz.pawel.Unichess.Backend.exception.ErrorType;
import banasiewicz.pawel.Unichess.Backend.model.Player;
import banasiewicz.pawel.Unichess.Backend.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Test
    @Transactional
    void getPlayers_shouldReturnEmptyList_whenNoPlayersExist() {
        final List<PlayerResponseDto> players = playerService.getPlayers();
        assertNotNull(players);
        assertTrue(players.isEmpty());
    }

    @Test
    @Transactional
    void getPlayerById_ShouldReturnProperPlayer_whenSelectedPlayerExists() {
        final PlayerCreateDto playerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                LocalDate.of(1990, 11, 30), Player.Sex.MALE, "Norway", "GM", 2855);

        final PlayerResponseDto addedPlayerResponseDto = playerService.addPlayer(playerCreateDto);
        assertNotNull(addedPlayerResponseDto);

        final PlayerResponseDto playerByIdResponseDto = playerService.getPlayerById(addedPlayerResponseDto.id());
        assertNotNull(addedPlayerResponseDto);

        checkPlayerResponseDtoWithPlayerCreateDto(playerByIdResponseDto, playerCreateDto);
    }

    @Test
    @Transactional
    void getPlayers_shouldReturnAllAddedPlayers_whenPlayersWereAdded() {
        final PlayerCreateDto firstPlayerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                LocalDate.of(1990, 11, 30), Player.Sex.MALE, "Norway", "GM", 2855);

        playerService.addPlayer(firstPlayerCreateDto);

        final PlayerCreateDto secondPlayerCreateDto = new PlayerCreateDto("Garry", "Kasparov",
                LocalDate.of(1963, 4, 13), Player.Sex.MALE, "Russia", "GM", 2855);

        playerService.addPlayer(secondPlayerCreateDto);

        final List<PlayerResponseDto> players = playerService.getPlayers();
        assertNotNull(players);
        assertEquals(2, players.size());

        checkPlayerResponseDtoWithPlayerCreateDto(players.getFirst(), firstPlayerCreateDto);
        checkPlayerResponseDtoWithPlayerCreateDto(players.get(1), secondPlayerCreateDto);
    }

    @Test
    @Transactional
    void addPlayer_shouldAddPlayerWithProperTitle_whenTitleIdIsValidAndVerboseName() {
        final PlayerCreateDto grandmasterPlayerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                LocalDate.of(1990, 11, 30), Player.Sex.MALE, "Norway", "Grandmaster", 2855);
        final PlayerResponseDto grandmasterPlayerResponseDto = playerService.addPlayer(grandmasterPlayerCreateDto);
        assertNotNull(grandmasterPlayerResponseDto);
        assertEquals("GM", grandmasterPlayerResponseDto.title());


        final PlayerCreateDto internationalMasterPlayerCreateDto = new PlayerCreateDto("Anna", "Muzychuk",
                LocalDate.of(1990, 2, 28), Player.Sex.FEMALE, "Ukraine", "International Master", 2500);

        final PlayerResponseDto internationalMasterPlayerResponseDto = playerService.addPlayer(internationalMasterPlayerCreateDto);
        assertNotNull(internationalMasterPlayerCreateDto);
        assertEquals("IM", internationalMasterPlayerResponseDto.title());

        final PlayerCreateDto fideMasterPlayerCreateDto = new PlayerCreateDto("John", "Doe",
                LocalDate.of(1985, 6, 15), Player.Sex.MALE, "United States", "FIDE Master", 2300);

        final PlayerResponseDto fideMasterPlayerResponseDto = playerService.addPlayer(fideMasterPlayerCreateDto);
        assertNotNull(fideMasterPlayerCreateDto);
        assertEquals("FM", fideMasterPlayerResponseDto.title());

        final PlayerCreateDto candidateMasterPlayerCreateDto = new PlayerCreateDto("Jane", "Smith",
                LocalDate.of(1995, 4, 10), Player.Sex.FEMALE, "Canada", "Candidate Master", 2100);

        final PlayerResponseDto candidateMasterPlayerResponseDto = playerService.addPlayer(candidateMasterPlayerCreateDto);
        assertNotNull(candidateMasterPlayerResponseDto);
        assertEquals("CM", candidateMasterPlayerResponseDto.title());

        final PlayerCreateDto womanGrandmasterPlayerCreateDto = new PlayerCreateDto("Zhao", "Xue",
                LocalDate.of(1985, 4, 6), Player.Sex.FEMALE, "China", "Woman Grandmaster", 2450);

        final PlayerResponseDto womanGrandmasterPlayerResponseDto = playerService.addPlayer(womanGrandmasterPlayerCreateDto);
        assertNotNull(womanGrandmasterPlayerResponseDto);
        assertEquals("WGM", womanGrandmasterPlayerResponseDto.title());

        final PlayerCreateDto womanInternationalMasterPlayerCreateDto = new PlayerCreateDto("Elena", "Kashlinskaya",
                LocalDate.of(1997, 11, 20), Player.Sex.FEMALE, "Russia", "Woman International Master", 2300);

        final PlayerResponseDto womanInternationalMasterPlayerResponseDto = playerService.addPlayer(womanInternationalMasterPlayerCreateDto);
        assertNotNull(womanInternationalMasterPlayerResponseDto);
        assertEquals("WIM", womanInternationalMasterPlayerResponseDto.title());

        final PlayerCreateDto womanFideMasterPlayerCreateDto = new PlayerCreateDto("Olga", "Girya",
                LocalDate.of(1991, 6, 4), Player.Sex.FEMALE, "Russia", "Woman FIDE Master", 2150);

        final PlayerResponseDto womanFideMasterPlayerResponseDto = playerService.addPlayer(womanFideMasterPlayerCreateDto);
        assertNotNull(womanFideMasterPlayerResponseDto);
        assertEquals("WFM", womanFideMasterPlayerResponseDto.title());

        final PlayerCreateDto nationalMasterPlayerCreateDto = new PlayerCreateDto("Mark", "Johnson",
                LocalDate.of(2003, 3, 8), Player.Sex.MALE, "England", "National Master", 2200);

        final PlayerResponseDto nationalMasterPlayerResponseDto = playerService.addPlayer(nationalMasterPlayerCreateDto);
        assertNotNull(nationalMasterPlayerResponseDto);
        assertEquals("NM", nationalMasterPlayerResponseDto.title());
    }

    @Test
    @Transactional
    void addPlayer_shouldAddPlayer_whenOnlyAllRequireFieldsAreProvided() {
        final PlayerCreateDto playerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                LocalDate.of(1990, 11, 30), null, null, null, null);
        final PlayerResponseDto playerResponseDto = playerService.addPlayer(playerCreateDto);
        assertNotNull(playerResponseDto);
        checkPlayerResponseDtoWithPlayerCreateDto(playerResponseDto, playerCreateDto);
    }

    @Test
    @Transactional
    void putPlayer_shouldUpdateEntireEntity_whenNoConflictsExist() {
        final PlayerCreateDto firstPlayerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                LocalDate.of(1990, 11, 30), Player.Sex.MALE, "Norway", "GM", 2855);

        final PlayerResponseDto firstPlayerResponseDto = playerService.addPlayer(firstPlayerCreateDto);

        final PlayerCreateDto secondPlayerCreateDto = new PlayerCreateDto("Garry", "Kasparov",
                LocalDate.of(1963, 4, 13), Player.Sex.MALE, "Russia", "GM", 2855);

        playerService.addPlayer(secondPlayerCreateDto);

        final PlayerCreateDto putPlayerCreateDto = new PlayerCreateDto("Anna", "Muzychuk",
                LocalDate.of(1990, 2, 28), Player.Sex.FEMALE, "Ukraine", "IM", 2500);

        final PlayerResponseDto putPlayerResponseDto = playerService.putPlayer(firstPlayerResponseDto.id(), putPlayerCreateDto);
        assertNotNull(putPlayerResponseDto);
        checkPlayerResponseDtoWithPlayerCreateDto(putPlayerResponseDto, putPlayerCreateDto);

        final PlayerResponseDto firstPlayerById = playerService.getPlayerById(firstPlayerResponseDto.id());
        assertNotNull(firstPlayerById);
        checkTwoPlayerResponseDtoEquality(firstPlayerById, putPlayerResponseDto);
    }

    @Test
    @Transactional
    void putPlayer_shouldUpdateEntireEntity_whenOnlyAllRequireFieldsAreProvided() {
        final PlayerCreateDto firstPlayerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                LocalDate.of(1990, 11, 30), Player.Sex.MALE, "Norway", "GM", 2855);

        final PlayerResponseDto firstPlayerResponseDto = playerService.addPlayer(firstPlayerCreateDto);

        final PlayerCreateDto secondPlayerCreateDto = new PlayerCreateDto("Garry", "Kasparov",
                LocalDate.of(1963, 4, 13), Player.Sex.MALE, "Russia", "GM", 2855);

        playerService.addPlayer(secondPlayerCreateDto);

        final PlayerCreateDto putPlayerCreateDto = new PlayerCreateDto("Anna", "Muzychuk",
                LocalDate.of(1990, 2, 28), null, null, null, null);

        final PlayerResponseDto putPlayerResponseDto = playerService.putPlayer(firstPlayerResponseDto.id(), putPlayerCreateDto);
        assertNotNull(putPlayerResponseDto);
        checkPlayerResponseDtoWithPlayerCreateDto(putPlayerResponseDto, putPlayerCreateDto);

        final PlayerResponseDto firstPlayerById = playerService.getPlayerById(firstPlayerResponseDto.id());
        assertNotNull(firstPlayerById);
        checkTwoPlayerResponseDtoEquality(firstPlayerById, putPlayerResponseDto);
    }

    @Test
    @Transactional
    void putPlayer_shouldUpdateEntireEntity_whenDataIsIdenticalToSelectedPlayer() {
        final PlayerCreateDto firstPlayerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                LocalDate.of(1990, 11, 30), Player.Sex.MALE, "Norway", "GM", 2855);

        final PlayerResponseDto firstPlayerResponseDto = playerService.addPlayer(firstPlayerCreateDto);

        final PlayerResponseDto putPlayerResponseDto = playerService.putPlayer(firstPlayerResponseDto.id(), firstPlayerCreateDto);
        assertNotNull(putPlayerResponseDto);
        checkPlayerResponseDtoWithPlayerCreateDto(putPlayerResponseDto, firstPlayerCreateDto);

        final PlayerResponseDto firstPlayerById = playerService.getPlayerById(firstPlayerResponseDto.id());
        assertNotNull(firstPlayerById);
        checkTwoPlayerResponseDtoEquality(firstPlayerById, firstPlayerResponseDto);
    }

    @Test
    @Transactional
    void putPlayer_shouldThrowDomainExceptionWithPlayerAlreadyExistError_whenPlayerAlreadyExistAndIsDifferentThanSelected() {
        final PlayerCreateDto firstPlayerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                LocalDate.of(1990, 11, 30), Player.Sex.MALE, "Norway", "GM", 2855);

        final PlayerResponseDto firstPlayerResponseDto = playerService.addPlayer(firstPlayerCreateDto);
        assertNotNull(firstPlayerResponseDto);

        final PlayerCreateDto secondPlayerCreateDto = new PlayerCreateDto("Garry", "Kasparov",
                LocalDate.of(1963, 4, 13), Player.Sex.MALE, "Russia", "GM", 2855);
        final PlayerResponseDto secondPlayerResponseDto = playerService.addPlayer(secondPlayerCreateDto);
        assertNotNull(secondPlayerResponseDto);

        final DomainException domainException = assertThrows(DomainException.class, () -> playerService.putPlayer(secondPlayerResponseDto.id(), firstPlayerCreateDto));
        assertEquals(ErrorType.PLAYER_ALREADY_EXIST, domainException.getErrorType());

        final PlayerResponseDto firstPlayerById = playerService.getPlayerById(firstPlayerResponseDto.id());
        assertNotNull(firstPlayerById);
        checkTwoPlayerResponseDtoEquality(firstPlayerById, firstPlayerResponseDto);
    }

    @Test
    @Transactional
    void patchPlayer_shouldChangeOnlySelectedFields_whenValidDataIsProvided() {
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);
        final Player.Sex sex = Player.Sex.MALE;
        final String nationality = "Norway";
        final String title = "GM";
        final int eloRating = 2855;

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                birthDate, sex, nationality, title, eloRating);
        final PlayerResponseDto firstPlayerResponseDto = playerService.addPlayer(playerCreateDto);
        assertNotNull(firstPlayerResponseDto);

        final String changedFirstName = "Garry";
        final String changedLastName = "Kasparov";
        final PlayerPatchDto playerPatchDto = new PlayerPatchDto(changedFirstName, changedLastName, null, null, null, null, null);
        final PlayerResponseDto playerResponseDto = playerService.patchPlayer(firstPlayerResponseDto.id(), playerPatchDto);
        assertNotNull(playerResponseDto);

        assertEquals(changedFirstName, playerResponseDto.firstName());
        assertEquals(changedLastName, playerResponseDto.lastName());
        assertEquals(birthDate, playerResponseDto.birthDate());
        assertEquals(sex, playerResponseDto.sex());
        assertEquals(nationality, playerResponseDto.nationality());
        assertEquals(title, playerResponseDto.title());
        assertEquals(eloRating, playerResponseDto.eloRating());
    }

    @Test
    @Transactional
    void patchPlayer_shouldAllowUpdatingPlayerWithIdenticalData_whenPatchingSamePlayer() {
        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto(firstName, lastName,
                birthDate, Player.Sex.MALE, "Norway", "GM", 2855);
        final PlayerResponseDto firstPlayerResponseDto = playerService.addPlayer(playerCreateDto);
        assertNotNull(firstPlayerResponseDto);

        final PlayerPatchDto playerPatchDto = new PlayerPatchDto(firstName, lastName, birthDate, null, null, null, null);

        final PlayerResponseDto afeterPatchPlayerResponseDto = playerService.patchPlayer(firstPlayerResponseDto.id(), playerPatchDto);
        checkTwoPlayerResponseDtoEquality(firstPlayerResponseDto, afeterPatchPlayerResponseDto);
    }

    @Test
    @Transactional
    void patchPlayer_shouldThrowDomainExceptionWithPlayerAlreadyExist__whenUpdatingPlayerToDuplicateExistingPlayerData() {
        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", "GM", 2855);
        final PlayerResponseDto firstPlayerResponseDto = playerService.addPlayer(playerCreateDto);
        assertNotNull(firstPlayerResponseDto);

        final PlayerCreateDto secondPlayerCreateDto = new PlayerCreateDto("Garry", "Kasparov",
                LocalDate.of(1963, 4, 13), Player.Sex.MALE, "Russia", "GM", 2855);
        final PlayerResponseDto secondPlayerResponseDto = playerService.addPlayer(secondPlayerCreateDto);
        assertNotNull(secondPlayerResponseDto);

        final PlayerPatchDto playerPatchDto = new PlayerPatchDto(firstName, lastName, birthDate, null, null, null, null);

        final DomainException domainException = assertThrows(DomainException.class, () -> playerService.patchPlayer(secondPlayerResponseDto.id(), playerPatchDto));
        assertEquals(ErrorType.PLAYER_ALREADY_EXIST, domainException.getErrorType());

        final PlayerResponseDto firstPlayerById = playerService.getPlayerById(firstPlayerResponseDto.id());
        assertNotNull(firstPlayerById);
        checkTwoPlayerResponseDtoEquality(firstPlayerById, firstPlayerResponseDto);
    }

    @Test
    @Transactional
    void deletePlayer_ShouldDeletePlayer_whenSelectedPlayerExists() {
        final PlayerCreateDto playerCreateDto = new PlayerCreateDto("Magnus", "Carlsen",
                LocalDate.of(1990, 11, 30), Player.Sex.MALE, "Norway", "Grandmaster", 2855);
        final PlayerResponseDto playerResponseDto = playerService.addPlayer(playerCreateDto);
        assertNotNull(playerResponseDto);

        playerService.deletePlayer(playerResponseDto.id());
        assertThrows(DomainException.class, () -> playerService.getPlayerById(playerResponseDto.id()));
    }

    private void checkPlayerResponseDtoWithPlayerCreateDto(final PlayerResponseDto playerResponseDto, final PlayerCreateDto playerCreateDto) {
        assertEquals(playerResponseDto.firstName(), playerCreateDto.firstName());
        assertEquals(playerResponseDto.lastName(), playerCreateDto.lastName());
        assertEquals(playerResponseDto.birthDate(), playerCreateDto.birthDate());
        assertEquals(playerResponseDto.sex(), playerCreateDto.sex());
        assertEquals(playerResponseDto.title(), playerCreateDto.title());
        assertEquals(playerResponseDto.eloRating(), playerCreateDto.eloRating());
    }

    private void checkTwoPlayerResponseDtoEquality(final PlayerResponseDto firstPlayerResponseDto, final PlayerResponseDto secondPlayerResponseDto) {
        assertEquals(firstPlayerResponseDto.firstName(), secondPlayerResponseDto.firstName());
        assertEquals(firstPlayerResponseDto.lastName(), secondPlayerResponseDto.lastName());
        assertEquals(firstPlayerResponseDto.birthDate(), secondPlayerResponseDto.birthDate());
        assertEquals(firstPlayerResponseDto.sex(), secondPlayerResponseDto.sex());
        assertEquals(firstPlayerResponseDto.title(), secondPlayerResponseDto.title());
        assertEquals(firstPlayerResponseDto.eloRating(), secondPlayerResponseDto.eloRating());
    }
}
