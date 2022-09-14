package de.unistuttgart.finitequizbackend.controller;

import de.unistuttgart.finitequizbackend.data.GameResultDTO;
import de.unistuttgart.finitequizbackend.service.GameResultService;
import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/results")
@Slf4j
public class GameResultController {

  @Autowired
  GameResultService gameResultService;

  @Value("${keycloak.issuer}")
  private String keycloakIssuer;
  @Value("${keycloak.url}")
  private String keycloakUrl;

  private JWTValidatorService jwtValidatorService;
  @Autowired
  private void setJWTValidatorService() throws MalformedURLException {
    jwtValidatorService = new JWTValidatorService(keycloakIssuer, keycloakUrl);
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public GameResultDTO saveGameResult(@CookieValue("access_token") final String accessToken, @RequestBody final GameResultDTO gameResultDTO) {
    final String userId = jwtValidatorService.validate(accessToken).getSubject();
    log.info("save game result for userId {}: {}", userId, gameResultDTO);
    gameResultService.saveGameResult(gameResultDTO, userId);
    return gameResultDTO;
  }
}
