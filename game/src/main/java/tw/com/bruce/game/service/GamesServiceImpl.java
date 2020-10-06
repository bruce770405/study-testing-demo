package tw.com.bruce.game.service;

import org.springframework.stereotype.Service;
import tw.com.bruce.game.entity.Game;
import tw.com.bruce.game.repository.Games;
import tw.com.bruce.game.repository.IgdbGateway;

import javax.json.JsonArray;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

@Service
public class GamesServiceImpl implements GamesService {

    private Games gameRepository;

    private IgdbGateway igdbGateway;

    @Override
    public Game searchGameById(final long gameId) throws IOException {

        final Optional<Game> foundGame = gameRepository.findGameById(gameId);

        if (isGameInSiteDatabase(foundGame)) {
            return foundGame.get();
        } else {
            final JsonArray jsonByGameId = igdbGateway.searchGameById(gameId);
            final Game game = Game.fromJson(jsonByGameId);
            gameRepository.create(game);
            return game;
        }

    }

    private boolean isGameInSiteDatabase(Optional<Game> foundGame) {
        return foundGame.isPresent();
    }

    @Override
    public List searchGames(String query) {
        return null;
    }
}
