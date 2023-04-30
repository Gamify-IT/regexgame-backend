package de.unistuttgart.regexgamebackend.service;

import de.unistuttgart.regexgamebackend.data.Configuration;
import de.unistuttgart.regexgamebackend.repository.ConfigurationRepository;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ConfigurationService {

    @Autowired
    ConfigurationRepository configurationRepository;

    /**
     * Get all configurations
     *
     * @return a list of all configurations
     */
    public List<Configuration> findAll() {
        return configurationRepository.findAll();
    }

    /**
     * Get a configuration by id
     *
     * @param id the id of the configuration to retrieve
     * @return the found configuration
     * @throws ResponseStatusException if the configuration could not be found
     */
    public Configuration getConfiguration(final @NonNull UUID id) {
        return configurationRepository
            .findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("There is no configuration with id %s", id)
                )
            );
    }

    /**
     * Save a configuration
     *
     * @param configuration the configuration to save
     * @return the saved configuration
     */
    public Configuration saveConfiguration(final @Valid @NonNull Configuration configuration) {
        return configurationRepository.save(configuration);
    }

    /**
     * Update a configuration
     *
     * @param configuration the configuration to update
     * @throws ResponseStatusException if the configuration doesn't exist
     * @return the updated configuration
     */
    public Configuration updateConfiguration(final @Valid @NonNull Configuration configuration) {
        if (!configurationRepository.existsById(configuration.getId())) throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("There is no configuration with id %s", configuration.getId())
        );
        return configurationRepository.save(configuration);
    }

    /**
     * Delete a configuration
     *
     * @param id the id of the configuration to delete
     * @return the deleted configuration
     */
    public Configuration deleteConfiguration(final @NonNull UUID id) {
        Configuration configuration = getConfiguration(id);
        configurationRepository.deleteById(id);
        return configuration;
    }

    /**
     * Clone a configuration
     * 
     * @param id id of a given configuration
     * @return id of the new configuration of equal content
     */
    public UUID cloneConfiguration(final UUID id) {
        final Configuration config = configurationRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Configuration with id %s not found", id)));
        final Configuration clonedConfig = config.clone();
        final Configuration savedConfig = configurationRepository.save(clonedConfig);
        return savedConfig.getId();
    }
}
