package de.unistuttgart.regexgamebackend.controller;

import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import de.unistuttgart.regexgamebackend.data.GameResult;
import de.unistuttgart.regexgamebackend.service.GameResultService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/results")
public class GameResultController {

    @Autowired
    private GameResultService gameResultService;

    @Autowired
    private JWTValidatorService jwtValidatorService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public GameResult saveGameResult(
        @CookieValue("access_token") final String accessToken,
        @RequestBody final GameResult gameResult
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        gameResult.setUserId(jwtValidatorService.extractUserId(accessToken));
        gameResult.setRewards(this.calculateRewards(gameResult.getScore()));
        gameResultService.submitGameResult(gameResult, accessToken);
        return gameResult;
    }

    /**
     * Calculates the amount of rewards given to the player.
     * The amount changes depending on the score, with a successful run giving 10 coins.
     *
     * @param score completion in percent
     * @return rewards (range 0 - 10)
     */
    private int calculateRewards(final long score) {
        return (int) score/10;
    }
}
