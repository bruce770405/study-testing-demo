package tw.com.bruce.demo.gateway;

import ch.qos.logback.core.net.server.Client;
import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.Future;

@Service
public class CommentsGateway {

    private final Client client;
    private final WebTarget games;
    private final String gamesHost;

    public CommentsGateway() {
        this.client = ClientBuilder.newClient();
        this.gamesHost = Optional.ofNullable(System.getenv("GAMES_SERVICE_URL"))
                .orElse(Optional.ofNullable(System.getProperty("GAMES_SERVICE_URL"))
                        .orElse("http://localhost:8181/"));

        this.games = this.client.target(gamesHost);
    }

    public Future<JsonObject> getCommentsFromCommentsService(final long gameId) {
        return this.games.path("{gameId}").resolveTemplate
                ("gameId", gameId)
                .register(JsonStructureBodyReader.class)
                .request(MediaType.APPLICATION_JSON).async()
                .get(JsonObject.class);
    }
}
