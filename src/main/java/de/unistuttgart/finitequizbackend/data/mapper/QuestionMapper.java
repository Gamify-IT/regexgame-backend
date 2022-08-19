package de.unistuttgart.finitequizbackend.data.mapper;

import de.unistuttgart.finitequizbackend.data.Question;
import de.unistuttgart.finitequizbackend.data.QuestionDTO;
import java.util.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
  QuestionDTO questionToQuestionDTO(final Question question);

  Question questionDTOToQuestion(final QuestionDTO questionDTO);

  Set<Question> questionDTOsToQuestions(final Set<QuestionDTO> questionDTOs);

  Set<QuestionDTO> questionsToQuestionDTOs(final Set<Question> questions);
}
