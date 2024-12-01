package banasiewicz.pawel.Unichess.Backend.config;

import banasiewicz.pawel.Unichess.Backend.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.HandlerMethod;

import java.util.Map;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Pawel",
                        url = "https://github.com/PawelBanasiewicz"
                ),
                description = "OpenAPI documentation for Unichess Backend",
                title = "OpenApi specification",
                version = "1.0"
        )
)
public class OpenApiConfig {

    private final static Map<String, String> ERROR_DESCRIPTIONS = Map.of(
            "400", "Invalid input data (e.g., validation failed)",
            "404", "Entry not found",
            "409", "Duplicated entry"
    );

    private final MessageSource messageSource;


    @Autowired
    public OpenApiConfig(MessageSource messageSource) {
        this.messageSource = messageSource;

    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("ErrorResponse", new Schema<ErrorResponse>())
                );
    }

    @Bean
    public OperationCustomizer openApiResponseCustomizer() {
        return (operation, handlerMethod) -> {
            final ApiResponses responses = operation.getResponses();

            try {
                responses.forEach((statusCode, apiResponse) -> {
                    if (isSuccessStatusCode(statusCode)) {
                        final String messageCode = createMessageCode(handlerMethod);
                        final String message = messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
                        apiResponse.setDescription(message);
                    } else {
                        apiResponse.setDescription(ERROR_DESCRIPTIONS.get(statusCode));
                    }
                });
            } catch (Exception ignored) {
            }

            return operation;
        };
    }

    private boolean isSuccessStatusCode(final String statusCode) {
        return statusCode.startsWith("2");
    }

    private String createMessageCode(final HandlerMethod handlerMethod) {
        final String controllerName = handlerMethod.getBeanType().getSimpleName().replace("Controller", "").toLowerCase();
        final String methodName = handlerMethod.getMethod().getName();
        return String.format("success.%s.%s", controllerName, methodName);
    }
}
