package banasiewicz.pawel.Unichess.Backend.integration.initialData;

import banasiewicz.pawel.Unichess.Backend.dto.opening.OpeningResponseDto;
import banasiewicz.pawel.Unichess.Backend.model.Opening;
import banasiewicz.pawel.Unichess.Backend.service.OpeningService;
import banasiewicz.pawel.Unichess.Backend.util.InitialDataLoader;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OpeningsInitialDataTest {

    @Autowired
    private OpeningService openingService;

    @Test
    public void getTitles_shouldReturnProperInitialData() {
        final List<Opening> openingsFromJson = InitialDataLoader.loadData("testInitialData/openingsInitialData.json", new TypeReference<>() {
        });
        final List<OpeningResponseDto> initialOpeningsFromJson = openingsFromJson.stream()
                .map(OpeningResponseDto::from)
                .toList();

        final List<OpeningResponseDto> initialOpeningsFromDatabase = openingService.getOpenings();

        assertIterableEquals(initialOpeningsFromJson, initialOpeningsFromDatabase);
    }
}