package tw.com.bruce.game.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tw.com.bruce.game.entity.Game;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GamesTest {

    private static final long GAME_ID = 123L;

    @InjectMocks
    private Games games;

    @Mock
    private EntityManager em;

    @Test
    void shouldCreateAGame() {
        // mock data
        Game game = new Game();
        game.setId(GAME_ID);
        game.setTitle("title");
        game.setCover("cover");

        // mock
        doReturn(game).when(em).merge(any(Game.class));

        Long gameId = games.create(game);

        // assert check
        assertThat(gameId).isEqualTo(GAME_ID);
        verify(em).merge(game);
    }

    @Test
    void shouldFindAGameById() {
        final Game game = new Game();
        game.setId(GAME_ID);
        game.setTitle("title");

        doReturn(game).when(em).find(any(), any());

        final Optional<Game> foundGame = games.findGameById(GAME_ID);

        verify(em).find(Game.class, GAME_ID);
        assertThat(foundGame).isNotNull().hasValue(game).usingFieldByFieldValueComparator();
    }

    /**
     * test entity return null.
     */
    @Test
    void shouldReturnAnEmptyOptionalIfElementNotFound() {
        final Game game = new Game();
        game.setId(GAME_ID);
        game.setTitle("title");

        doReturn(null).when(em.find(any(), any()));

        final Optional<Game> foundGame = games.findGameById(GAME_ID);

        verify(em).find(Game.class, GAME_ID);
        assertThat(foundGame).isNotPresent();
    }
}
