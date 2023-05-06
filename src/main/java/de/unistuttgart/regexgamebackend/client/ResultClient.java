package de.unistuttgart.regexgamebackend.client;

import de.unistuttgart.regexgamebackend.data.GameResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "resultClient", url = "${overworld.url}/internal")
public interface ResultClient {
    @PostMapping("/submit-game-pass")
    void submit(@CookieValue("access_token") final String accessToken, final GameResult gameResult);
}
