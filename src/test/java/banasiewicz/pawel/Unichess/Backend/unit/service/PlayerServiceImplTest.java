package banasiewicz.pawel.Unichess.Backend.unit.service;

import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerCreateDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerPatchDto;
import banasiewicz.pawel.Unichess.Backend.dto.player.PlayerResponseDto;
import banasiewicz.pawel.Unichess.Backend.exception.DomainException;
import banasiewicz.pawel.Unichess.Backend.exception.ErrorType;
import banasiewicz.pawel.Unichess.Backend.model.Player;
import banasiewicz.pawel.Unichess.Backend.model.Title;
import banasiewicz.pawel.Unichess.Backend.repository.PlayerRepository;
import banasiewicz.pawel.Unichess.Backend.repository.TitleRepository;
import banasiewicz.pawel.Unichess.Backend.service.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TitleRepository titleRepository;

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @Test
    void getPlayerById_shouldThrowDomainExceptionWithPlayerNotFoundError_whenPlayerWasNotFound() {
        final long nonExistingPlayerId = 1;

        when(playerRepository.findById(nonExistingPlayerId)).thenReturn(Optional.empty());

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.getPlayerById(nonExistingPlayerId));
        verify(playerRepository, times(1)).findById(nonExistingPlayerId);
        assertEquals(ErrorType.PLAYER_NOT_FOUND, domainException.getErrorType());
        assertEquals(nonExistingPlayerId, domainException.getParams()[0]);
    }

    @Test
    void getPlayerById_shouldNotThrowDomainException_whenPlayerWasFound() {
        final long existingPlayerId = 1;

        final Player mockedPlayer = mock(Player.class);
        when(playerRepository.findById(existingPlayerId)).thenReturn(Optional.of(mockedPlayer));

        final PlayerResponseDto playerResponseDto = assertDoesNotThrow(() -> playerServiceImpl.getPlayerById(existingPlayerId));
        assertNotNull(playerResponseDto);
        verify(playerRepository, times(1)).findById(existingPlayerId);
    }

    @Test
    void addPlayer_shouldThrowDomainExceptionWithPlayerAlreadyExistError_whenPlayerAlreadyExist() {
        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", "GM", 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate)).thenReturn(true);

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.addPlayer(playerCreateDto));
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);
        verify(titleRepository, times(0)).findByNameOrAbbreviationIgnoreCase(any(String.class));
        assertEquals(ErrorType.PLAYER_ALREADY_EXIST, domainException.getErrorType());
        assertEquals(firstName, domainException.getParams()[0]);
        assertEquals(lastName, domainException.getParams()[1]);
        assertEquals(birthDate, domainException.getParams()[2]);
    }

    @Test
    void addPlayer_shouldThrowDomainExceptionWithTitleNotFoundError_whenTitleWasNotFound() {
        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);
        final String nonExistingTitle = "abc";

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", nonExistingTitle, 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate)).thenReturn(false);
        when(titleRepository.findByNameOrAbbreviationIgnoreCase(nonExistingTitle)).thenReturn(Optional.empty());

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.addPlayer(playerCreateDto));
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);
        verify(titleRepository, times(1)).findByNameOrAbbreviationIgnoreCase(nonExistingTitle);
        assertEquals(ErrorType.TITLE_NOT_FOUND, domainException.getErrorType());
        assertEquals(nonExistingTitle, domainException.getParams()[0]);
    }

    @Test
    void addPlayer_shouldInvokeSave_whenProvidedDataIsCorrect() {
        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);
        final String title = "GM";

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", title, 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate)).thenReturn(false);
        final Title mockedGmTitle = mock(Title.class);
        when(titleRepository.findByNameOrAbbreviationIgnoreCase(title)).thenReturn(Optional.of(mockedGmTitle));

        final Player dummySavedPlayer = new Player();
        when(playerRepository.save(any(Player.class))).thenReturn(dummySavedPlayer);

        final PlayerResponseDto playerResponseDto = playerServiceImpl.addPlayer(playerCreateDto);
        assertNotNull(playerResponseDto);
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);
        verify(titleRepository, times(1)).findByNameOrAbbreviationIgnoreCase(title);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void putPlayer_ShouldThrowDomainExceptionWithPlayerAlreadyExistError_whenPlayerAlreadyExistAndIsDifferentThanSelected() {
        final long selectedPlayerId = 1;

        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", "GM", 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate)).thenReturn(true);

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.putPlayer(selectedPlayerId, playerCreateDto));
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate);
        verify(titleRepository, times(0)).findByNameOrAbbreviationIgnoreCase(any(String.class));
        verify(playerRepository, times(0)).findById(selectedPlayerId);
        assertEquals(ErrorType.PLAYER_ALREADY_EXIST, domainException.getErrorType());
        assertEquals(firstName, domainException.getParams()[0]);
        assertEquals(lastName, domainException.getParams()[1]);
        assertEquals(birthDate, domainException.getParams()[2]);
    }

    @Test
    void putPlayer_ShouldThrowDomainExceptionWithTitleNotFound_whenTitleWasNotFound() {
        final long selectedPlayerId = 1;

        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);
        final String nonExistingTitle = "abc";

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", nonExistingTitle, 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate)).thenReturn(false);
        when(titleRepository.findByNameOrAbbreviationIgnoreCase(nonExistingTitle)).thenReturn(Optional.empty());

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.putPlayer(selectedPlayerId, playerCreateDto));
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate);
        verify(titleRepository, times(1)).findByNameOrAbbreviationIgnoreCase(nonExistingTitle);
        verify(playerRepository, times(0)).findById(selectedPlayerId);
        assertEquals(ErrorType.TITLE_NOT_FOUND, domainException.getErrorType());
        assertEquals(nonExistingTitle, domainException.getParams()[0]);
    }

    @Test
    void putPlayer_ShouldThrowDomainExceptionWithPlayerNotFoundError_whenPlayerWasNotFound() {
        final long selectedPlayerId = 1;

        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);
        final String title = "GM";

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", title, 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate)).thenReturn(false);
        final Title mockedGmTitle = mock(Title.class);
        when(titleRepository.findByNameOrAbbreviationIgnoreCase(title)).thenReturn(Optional.of(mockedGmTitle));
        when(playerRepository.findById(selectedPlayerId)).thenReturn(Optional.empty());

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.putPlayer(selectedPlayerId, playerCreateDto));
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate);
        verify(titleRepository, times(1)).findByNameOrAbbreviationIgnoreCase(title);
        verify(playerRepository, times(1)).findById(selectedPlayerId);
        assertEquals(ErrorType.PLAYER_NOT_FOUND, domainException.getErrorType());
        assertEquals(selectedPlayerId, domainException.getParams()[0]);
    }

    @Test
    void putPlayer_shouldInvokeSave_whenProvidedDataIsCorrect() {
        final long selectedPlayerId = 1;

        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);
        final String title = "GM";

        final PlayerCreateDto playerCreateDto = new PlayerCreateDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", title, 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate)).thenReturn(false);
        final Title mockedGmTitle = mock(Title.class);
        when(titleRepository.findByNameOrAbbreviationIgnoreCase(title)).thenReturn(Optional.of(mockedGmTitle));

        final Player dummySavedPlayer = new Player();
        when(playerRepository.findById(selectedPlayerId)).thenReturn(Optional.of(dummySavedPlayer));
        when(playerRepository.save(any(Player.class))).thenReturn(dummySavedPlayer);

        final PlayerResponseDto playerResponseDto = playerServiceImpl.putPlayer(selectedPlayerId, playerCreateDto);
        assertNotNull(playerResponseDto);
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate);
        verify(titleRepository, times(1)).findByNameOrAbbreviationIgnoreCase(title);
        verify(playerRepository, times(1)).findById(selectedPlayerId);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void patchPlayer_ShouldThrowDomainExceptionWithPlayerAlreadyExistError_whenPlayerAlreadyExistAndIsDifferentThanSelected() {
        final long selectedPlayerId = 1;

        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);

        final PlayerPatchDto playerPatchDto = new PlayerPatchDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", "GM", 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate)).thenReturn(true);

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.patchPlayer(selectedPlayerId, playerPatchDto));
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate);
        verify(titleRepository, times(0)).findByNameOrAbbreviationIgnoreCase(any(String.class));
        verify(playerRepository, times(0)).findById(selectedPlayerId);
        assertEquals(ErrorType.PLAYER_ALREADY_EXIST, domainException.getErrorType());
        assertEquals(firstName, domainException.getParams()[0]);
        assertEquals(lastName, domainException.getParams()[1]);
        assertEquals(birthDate, domainException.getParams()[2]);
    }

    @Test
    void patchPlayer_ShouldThrowDomainExceptionWithTitleNotFound_whenTitleWasNotFound() {
        final long selectedPlayerId = 1;

        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);
        final String nonExistingTitle = "abc";

        final PlayerPatchDto playerPatchDto = new PlayerPatchDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", nonExistingTitle, 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate)).thenReturn(false);
        when(titleRepository.findByNameOrAbbreviationIgnoreCase(nonExistingTitle)).thenReturn(Optional.empty());

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.patchPlayer(selectedPlayerId, playerPatchDto));
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate);
        verify(titleRepository, times(1)).findByNameOrAbbreviationIgnoreCase(nonExistingTitle);
        verify(playerRepository, times(0)).findById(selectedPlayerId);
        assertEquals(ErrorType.TITLE_NOT_FOUND, domainException.getErrorType());
        assertEquals(nonExistingTitle, domainException.getParams()[0]);
    }

    @Test
    void patchPlayer_ShouldThrowDomainExceptionWithPlayerNotFoundError_whenPlayerWasNotFound() {
        final long selectedPlayerId = 1;

        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);
        final String title = "GM";

        final PlayerPatchDto playerPatchDto = new PlayerPatchDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", title, 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate)).thenReturn(false);
        final Title mockedGmTitle = mock(Title.class);
        when(titleRepository.findByNameOrAbbreviationIgnoreCase(title)).thenReturn(Optional.of(mockedGmTitle));
        when(playerRepository.findById(selectedPlayerId)).thenReturn(Optional.empty());

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.patchPlayer(selectedPlayerId, playerPatchDto));
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate);
        verify(titleRepository, times(1)).findByNameOrAbbreviationIgnoreCase(title);
        verify(playerRepository, times(1)).findById(selectedPlayerId);
        assertEquals(ErrorType.PLAYER_NOT_FOUND, domainException.getErrorType());
        assertEquals(selectedPlayerId, domainException.getParams()[0]);
    }

    @Test
    void patchPlayer_shouldInvokeSave_whenProvidedDataIsCorrect() {
        final long selectedPlayerId = 1;

        final String firstName = "Magnus";
        final String lastName = "Carlsen";
        final LocalDate birthDate = LocalDate.of(1990, 11, 30);
        final String title = "GM";

        final PlayerPatchDto playerPatchDto = new PlayerPatchDto(firstName, lastName, birthDate,
                Player.Sex.MALE, "Norway", title, 2850);

        when(playerRepository.existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate)).thenReturn(false);
        final Title mockedGmTitle = mock(Title.class);
        when(titleRepository.findByNameOrAbbreviationIgnoreCase(title)).thenReturn(Optional.of(mockedGmTitle));

        final Player dummySavedPlayer = new Player();
        when(playerRepository.findById(selectedPlayerId)).thenReturn(Optional.of(dummySavedPlayer));
        when(playerRepository.save(any(Player.class))).thenReturn(dummySavedPlayer);

        final PlayerResponseDto playerResponseDto = playerServiceImpl.patchPlayer(selectedPlayerId, playerPatchDto);
        assertNotNull(playerResponseDto);
        verify(playerRepository, times(1)).existsByFirstNameAndLastNameAndBirthDateExcludingId(selectedPlayerId, firstName, lastName, birthDate);
        verify(titleRepository, times(1)).findByNameOrAbbreviationIgnoreCase(title);
        verify(playerRepository, times(1)).findById(selectedPlayerId);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void deletePlayer_shouldThrowDomainExceptionWithPlayerNotFoundError_whenPlayerWasNotFound() {
        final long nonExistingPlayerId = 1;

        when(playerRepository.findById(nonExistingPlayerId)).thenReturn(Optional.empty());

        final DomainException domainException = assertThrows(DomainException.class, () -> playerServiceImpl.deletePlayer(nonExistingPlayerId));
        verify(playerRepository, times(1)).findById(nonExistingPlayerId);
        assertEquals(ErrorType.PLAYER_NOT_FOUND, domainException.getErrorType());
        assertEquals(nonExistingPlayerId, domainException.getParams()[0]);
    }

    @Test
    void deletePlayer_shouldInvokeDeleteById_whenPlayerExist() {
        final long existingPlayerId = 1;

        final Player mockedPlayer = mock(Player.class);
        when(playerRepository.findById(existingPlayerId)).thenReturn(Optional.of(mockedPlayer));

        playerServiceImpl.deletePlayer(existingPlayerId);
        verify(playerRepository, times(1)).findById(existingPlayerId);
        verify(playerRepository, times(1)).deleteById(existingPlayerId);
    }
}