package de.unistuttgart.regexgamebackend.controller;

import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import de.unistuttgart.regexgamebackend.data.Configuration;
import de.unistuttgart.regexgamebackend.service.ConfigurationService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configurations")
@Import({ JWTValidatorService.class })
@Validated
public class ConfigurationController {

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    private JWTValidatorService jwtValidatorService;

    @GetMapping("")
    public List<Configuration> getConfigurations(@CookieValue("access_token") final String accessToken) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        return configurationService.findAll();
    }

    @GetMapping("/{id}")
    public Configuration getConfiguration(
        @CookieValue("access_token") final String accessToken,
        @PathVariable @NonNull final UUID id
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        return configurationService.getConfiguration(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Configuration createConfiguration(
        @CookieValue("access_token") final String accessToken,
        @NonNull final Configuration configuration
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        jwtValidatorService.hasRolesOrThrow(accessToken, List.of("lecturer"));
        return configurationService.saveConfiguration(configuration);
    }

    @PutMapping("/{id}")
    public Configuration updateConfiguration(
        @CookieValue("access_token") final String accessToken,
        @PathVariable @NonNull final UUID id,
        @NonNull final Configuration configuration
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        jwtValidatorService.hasRolesOrThrow(accessToken, List.of("lecturer"));
        configuration.setId(id);
        return configurationService.updateConfiguration(configuration);
    }

    @DeleteMapping("/{id}")
    public Configuration deleteConfiguration(
        @CookieValue("access_token") final String accessToken,
        @PathVariable @NonNull final UUID id
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        jwtValidatorService.hasRolesOrThrow(accessToken, List.of("lecturer"));
        return configurationService.deleteConfiguration(id);
    }
}
