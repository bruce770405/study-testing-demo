package tw.com.bruce.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tw.com.bruce.demo.gateway.CommentsGateway;
import tw.com.bruce.demo.gateway.GamesGateway;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RestController
public class GamesController {

    @Autowired
    private GamesGateway gamesGateway;

    @Autowired
    private CommentsGateway commentsGateway;

    private final Executor executor = Executors.newFixedThreadPool(8);

    @GetMapping(value = "{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getGameInfo(@Suspended final AsyncResponse asyncResponse, @PathVariable("gameId") final long gameId) {

        asyncResponse.setTimeoutHandler(ar -> ar.resume(Response
                .status(Response.Status.SERVICE_UNAVAILABLE).entity("TIME OUT !").build()));

        asyncResponse.setTimeout(15, TimeUnit.SECONDS);

        final CompletableFuture<JsonObject> gamesGatewayFuture = Futures.toCompletable(gamesGateway.getGameFromGamesService(gameId), executor);
        final CompletableFuture<JsonObject> commentsGatewayFuture = Futures.toCompletable(commentsGateway.getCommentsFromCommentsService(gameId), executor);

        gamesGatewayFuture.thenCombine(commentsGatewayFuture,
                (g, c) -> Json.createObjectBuilder().add("game", g).add("comments", c).build())
                .thenApply(info -> asyncResponse.resume(Response.ok
                        (info).build())
                ).exceptionally(asyncResponse::resume);

    }
}
