package de.unistuttgart.singlechoicebackend.data.mapper;

import de.unistuttgart.singlechoicebackend.data.Configuration;
import de.unistuttgart.singlechoicebackend.data.ConfigurationDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfigurationMapper {
  ConfigurationDTO configurationToConfigurationDTO(final Configuration configuration);

  Configuration configurationDTOToConfiguration(final ConfigurationDTO configurationDTO);

  List<ConfigurationDTO> configurationsToConfigurationDTOs(final List<Configuration> configurations);
}
