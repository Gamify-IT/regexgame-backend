package de.unistuttgart.regexgamebackend.service;

import de.unistuttgart.regexgamebackend.client.ResultClient;
import de.unistuttgart.regexgamebackend.data.GameResult;
import feign.FeignException;
import java.lang.module.FindException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GameResultService {

    @Autowired
    ResultClient resultClient;

    public void submitGameResult(final GameResult gameResult, final String accessToken) {
        try {
            resultClient.submit(accessToken, gameResult);
        } catch (final FeignException.BadGateway badGateway) {
            throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "The overworld backend is currently unavailable."
            );
        } catch (final FeignException.NotFound notFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The result could not be found.");
        }
    }
}
