package banasiewicz.pawel.Unichess.Backend.integration.initialData;

import banasiewicz.pawel.Unichess.Backend.dto.title.TitleResponseDto;
import banasiewicz.pawel.Unichess.Backend.model.Title;
import banasiewicz.pawel.Unichess.Backend.service.TitleService;
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
public class TitlesInitialDataTest {

    @Autowired
    private TitleService titleService;

    @Test
    public void getTitles_shouldReturnProperInitialData() {
        final List<Title> titlesFromJson = InitialDataLoader.loadData("testInitialData/titlesInitialData.json", new TypeReference<>() {
        });
        final List<TitleResponseDto> initialTitlesFromJson = titlesFromJson.stream()
                .map(TitleResponseDto::from)
                .toList();

        final List<TitleResponseDto> initialTitlesFromDatabase = titleService.getTitles();

        assertIterableEquals(initialTitlesFromJson, initialTitlesFromDatabase);
    }
}
