package tw.com.bruce.game.service;

import tw.com.bruce.game.entity.Game;

import java.awt.*;
import java.io.IOException;

public interface GamesService {

    List searchGames(String query);

    Game searchGameById(final long gameId) throws IOException;
}
