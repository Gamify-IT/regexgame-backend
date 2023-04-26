package de.unistuttgart.regexgamebackend.controller;

import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import de.unistuttgart.regexgamebackend.data.Configuration;
import de.unistuttgart.regexgamebackend.data.RegexStructure;
import de.unistuttgart.regexgamebackend.service.ConfigurationService;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
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

    static class ConfigurationDTO {
        private UUID id;
        private EnumSet<RegexStructure> allowedRegexStructures;

        public UUID getId() {
            return id;
        }

        public EnumSet<RegexStructure> getAllowedRegexStructures() {
            return allowedRegexStructures;
        }
    }

    public static final List<String> LECTURER = List.of("lecturer");
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
        @NonNull final ConfigurationDTO configuration
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER);

        Configuration persistentConfiguration = new Configuration();
        persistentConfiguration.setAllowedRegexStructures(configuration.getAllowedRegexStructures());
        persistentConfiguration.setId(configuration.getId());

        return configurationService.saveConfiguration(persistentConfiguration);
    }

    @PutMapping("/{id}")
    public Configuration updateConfiguration(
        @CookieValue("access_token") final String accessToken,
        @PathVariable @NonNull final UUID id,
        @NonNull final ConfigurationDTO configuration
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER);

        Configuration persistentConfiguration = new Configuration();
        persistentConfiguration.setAllowedRegexStructures(configuration.getAllowedRegexStructures());
        persistentConfiguration.setId(id);

        return configurationService.updateConfiguration(persistentConfiguration);
    }

    @DeleteMapping("/{id}")
    public Configuration deleteConfiguration(
        @CookieValue("access_token") final String accessToken,
        @PathVariable @NonNull final UUID id
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER);
        return configurationService.deleteConfiguration(id);
    }
}
