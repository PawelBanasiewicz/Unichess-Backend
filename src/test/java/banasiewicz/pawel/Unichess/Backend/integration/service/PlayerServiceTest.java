package banasiewicz.pawel.Unichess.Backend.integration.service;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;
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
    void getPlayers_shouldReturnAllAddedPlayers_whenPlayersWereAdded() {
        final String firstPlayerFirstName = "Magnus";
        final String firstPlayerLastName = "Carlsen";
        final LocalDate firstPlayerBirthDate = LocalDate.of(1990, 11, 30);
        final Player.Sex firstPlayerSex = Player.Sex.MALE;
        final String firstPlayerNationality = "Norway";
        final String firstPlayerTitle = "GM";
        final int firstPlayerEloRating = 2855;

        final PlayerCreateDto firstPlayerCreateDto = new PlayerCreateDto(firstPlayerFirstName, firstPlayerLastName,
                firstPlayerBirthDate, firstPlayerSex, firstPlayerNationality, firstPlayerTitle, firstPlayerEloRating);

        playerService.addPlayer(firstPlayerCreateDto);

        final String secondPlayerFirstName = "Garry";
        final String secondPlayerLastName = "Kasparov";
        final LocalDate secondPlayerBirthDate =  LocalDate.of(1963, 4, 13);
        final Player.Sex secondPlayerSex = Player.Sex.MALE;
        final String secondNationality = "Russia";
        final String secondPlayerTitle = "GM";
        final int secondPlayerEloRating = 2855;

        final PlayerCreateDto secondPlayerCreateDto = new PlayerCreateDto(secondPlayerFirstName, secondPlayerLastName,
                secondPlayerBirthDate, secondPlayerSex, secondNationality, secondPlayerTitle, secondPlayerEloRating);

        playerService.addPlayer(secondPlayerCreateDto);


        final List<PlayerResponseDto> players = playerService.getPlayers();
        assertNotNull(players);
        assertEquals(2, players.size());

        checkPlayerResponseDtoWithPlayerCreateDto(players.getFirst(), firstPlayerCreateDto);
        checkPlayerResponseDtoWithPlayerCreateDto(players.get(1), secondPlayerCreateDto);
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
