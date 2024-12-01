package banasiewicz.pawel.Unichess.Backend.integration.service;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;
import banasiewicz.pawel.Unichess.Backend.exception.DomainException;
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
    void getPlayers_shouldReturnEmptyList_whenThereIsNoPlayer() {
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
}
