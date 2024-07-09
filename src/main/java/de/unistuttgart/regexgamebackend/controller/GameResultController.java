package de.unistuttgart.regexgamebackend.controller;

import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import de.unistuttgart.regexgamebackend.data.Configuration;
import de.unistuttgart.regexgamebackend.data.GameResult;
import de.unistuttgart.regexgamebackend.service.ConfigurationService;
import de.unistuttgart.regexgamebackend.service.GameResultService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/results")
public class GameResultController {

    @Autowired
    private GameResultService gameResultService;

    @Autowired
    private ConfigurationService configurationService;

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
        gameResult.setRewards(this.calculateRewards(gameResult));
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

    /**
     * Calculates the amount of rewards given to the player.
     * The amount changes depending on the score, for each 2 mandatory rounds completed,
     * the player will receive 1 coin. For every 4 endless rounds completed afterwards, the player
     * will receive 1 coin.
     *
     * @param result result of the game, received from the frontend
     * @return rewards
     */
    private int calculateRewards(final GameResult result) {
        int completedRounds = result.getCompletedRounds();
        int minimumRoundsForCompletion = getMinimumRoundsFromConfiguration(result.getConfigurationId());
        int reward = 0;
        int endlessRounds = completedRounds - minimumRoundsForCompletion;
        if(endlessRounds > 0) {
            reward += endlessRounds/4;
            reward += minimumRoundsForCompletion/2;
        } else {
            reward += completedRounds/2;
        }
        return reward;
    }

    private Integer getMinimumRoundsFromConfiguration(final UUID id) {
        Configuration config = configurationService.getConfiguration(id);
        return config.getMinimumCompletedRounds();
    }
}
