package tw.com.bruce.game.repository;

import tw.com.bruce.game.entity.Game;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * game's repository
 */
public class Games {

    @PersistenceContext
    private EntityManager em;

    public Games(){

    }

    public Long create(final Game request) {
        final Game game = em.merge(request);
        return game.getId();
    }

    public Optional<Game> findGameById(final Long gameId) {
        Optional<Game> g = Optional.ofNullable(em.find(Game.class,
                gameId));

        if (g.isPresent()) {
            Game game = g.get();
            game.getReleases().size();
            game.getPublishers().size();
            game.getDevelopers().size();
            em.detach(game);
        }

        return g;
    }

}
