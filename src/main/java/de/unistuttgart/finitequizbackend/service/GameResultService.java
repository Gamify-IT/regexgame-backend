package de.unistuttgart.finitequizbackend.service;

import de.unistuttgart.finitequizbackend.clients.ResultClient;
import de.unistuttgart.finitequizbackend.data.*;
import de.unistuttgart.finitequizbackend.repositories.GameResultRepository;
import de.unistuttgart.finitequizbackend.repositories.QuestionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@Transactional
public class GameResultService {

  @Autowired
  ResultClient resultClient;

  @Autowired
  GameResultRepository gameResultRepository;

  @Autowired
  QuestionRepository questionRepository;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * Cast list of question texts to a List of Questions
   *
   * @param roundResultDTOs list of RoundResults
   * @return a list of questions
   */
  public List<RoundResult> castQuestionList(final List<RoundResultDTO> roundResultDTOs) {
    List<RoundResult> questionList = new ArrayList<>();
    for (RoundResultDTO roundResultDTO : roundResultDTOs) {
      Optional<Question> questionToAdd = questionRepository.findById(roundResultDTO.getQuestionUUId());
      if (questionToAdd.isEmpty()) {
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          String.format("There is no question with uuid %s.", roundResultDTO.getQuestionUUId())
        );
      } else {
        RoundResult roundResult = new RoundResult(questionToAdd.get(), roundResultDTO.getAnswer());
        questionList.add(roundResult);
      }
    }
    return questionList;
  }

  /**
   * Casts a GameResultDTO to GameResult and saves it in the Database
   *
   * @param gameResultDTO extern gameResultDTO
   */
  public void saveGameResult(final GameResultDTO gameResultDTO, final String userId) {
    final int resultScore = calculateResultScore(gameResultDTO.getCorrectAnsweredQuestions().size(), gameResultDTO.getQuestionCount());
    OverworldResultDTO resultDTO = new OverworldResultDTO(
            "FINITEQUIZ",
            gameResultDTO.getConfigurationAsUUID(),
            resultScore,
            userId
    );
    try {
      resultClient.submit(resultDTO);
      List<RoundResult> correctQuestions = this.castQuestionList(gameResultDTO.getCorrectAnsweredQuestions());
      List<RoundResult> wrongQuestions = this.castQuestionList(gameResultDTO.getWrongAnsweredQuestions());
      GameResult result = new GameResult(
              gameResultDTO.getQuestionCount(),
              gameResultDTO.getScore(),
              correctQuestions,
              wrongQuestions,
              gameResultDTO.getConfigurationAsUUID(),
              userId
      );
      gameResultRepository.save(result);
    } catch (FeignException.BadGateway badGateway) {
      String warning = "The Overworld backend is currently not available. The result was NOT saved. Please try again later";
      log.warn(warning + badGateway);
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
              warning);
    } catch (FeignException.NotFound notFound) {
      String warning = "The result could not be saved. Unknown User";
      log.warn(warning + notFound);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
              warning);
    }
  }
  private int calculateResultScore(final int correctAnswers, final int numberOfQuestions) {
    return (int) ((100.0 * correctAnswers) / numberOfQuestions);
  }
}
