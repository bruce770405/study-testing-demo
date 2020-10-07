package tw.com.bruce.game.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tw.com.bruce.game.entity.Game;
import tw.com.bruce.game.repository.Games;
import tw.com.bruce.game.repository.IgdbGateway;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GamesServiceTest {

    private long id = 123L;

    @InjectMocks
    private GamesServiceImpl gamesService;

    @Mock
    private Games gameRepository;

    @Mock
    private IgdbGateway igdbGateway;

    @Test
    void shouldReturnGameIfItIsCachedInInternalDatabase() throws IOException {
        Game game = new Game();
        game.setId(1L);
        Optional<Game> optional = Optional.of(game);
        doReturn(optional).when(gameRepository).findGameById(any());
        Game returnGame = gamesService.searchGameById(123L);

        assertThat(returnGame).isEqualTo(game);
        verify(igdbGateway, times(0)).searchGameById(anyLong());
        verify(gameRepository).findGameById(123L);
    }

    @Test
    void shouldReturnGameFromIgdbSiteIfGameIsNotInInternalDatabase() throws IOException {
    }

}
