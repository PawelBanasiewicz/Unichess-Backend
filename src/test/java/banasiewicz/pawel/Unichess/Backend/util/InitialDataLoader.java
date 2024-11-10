package banasiewicz.pawel.Unichess.Backend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class InitialDataLoader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> loadData(final String resourceFilePath, TypeReference<List<T>> typeReference) {
        try (final InputStream inputStream = InitialDataLoader.class.getClassLoader().getResourceAsStream(resourceFilePath)) {

            if (inputStream == null) {
                fail("Resource not found: " + resourceFilePath);
            }

            return objectMapper.readValue(inputStream, typeReference);

        } catch (IOException e) {
            fail("Parsing initial data failed: " + e.getMessage());
        }
        return List.of();
    }
}
