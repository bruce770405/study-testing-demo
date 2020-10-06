package tw.com.bruce.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.com.bruce.game.service.GamesService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.directory.SearchResult;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;

@RequestMapping
@RestController
public class GamesController {

    @Autowired
    private GamesService gamesService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Async
    public void searchGames(@Suspended final AsyncResponse response, @NotNull @RequestParam("query") final String query) {

        response.setTimeoutHandler(asyncResponse -> asyncResponse
                .resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("TIME OUT !").build()));
        response.setTimeout(15, TimeUnit.SECONDS);

        try {
            final Collector<JsonObject, ?, JsonArrayBuilder> jsonCollector = Collector.of
                    (Json::createArrayBuilder,
                            JsonArrayBuilder::add, (left, right) -> {
                                left.add(right);
                                return left;
                            });

            final List<SearchResult> searchResults = gamesService.searchGames(query);

            final JsonArrayBuilder mappedGames = searchResults.stream().map(SearchResult::convertToJson)
                    .collect(jsonCollector);

            final ResponseEntity ok = ResponseEntity.ok(mappedGames.build());
            response.resume(ok.build());
        } catch (final Throwable e) {
            response.resume(e);
        }
    }
}
