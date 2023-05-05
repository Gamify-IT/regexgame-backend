package de.unistuttgart.regexgamebackend.controller;

import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import de.unistuttgart.regexgamebackend.data.GameResult;
import de.unistuttgart.regexgamebackend.service.GameResultService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/results")
public class GameResultController {

    @Autowired
    private GameResultService gameResultService;

    @Autowired
    private JWTValidatorService jwtValidatorService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void saveGameResult(
        @CookieValue("access_token") final String accessToken,
        @RequestBody final GameResult gameResult
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        gameResult.setUserId(jwtValidatorService.extractUserId(accessToken));
        gameResultService.submitGameResult(gameResult, accessToken);
    }
}
