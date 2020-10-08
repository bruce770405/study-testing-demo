package tw.com.bruce.game.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tw.com.bruce.game.entity.Game;
import tw.com.bruce.game.repository.Games;
import tw.com.bruce.game.repository.IgdbGateway;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GamesServiceTest {

    private final long id = 123L;

    private final String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

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
        game.setTitle("title");
        game.setCover("cover");
        doReturn(Optional.of(game)).when(gameRepository).findGameById(any());
        Game returnGame = gamesService.searchGameById(id);

        assertThat(returnGame).isEqualTo(game);
        verify(igdbGateway, times(0)).searchGameById(anyLong());
        verify(gameRepository).findGameById(id);
    }

    @Test
    void shouldReturnGameFromIgdbSiteIfGameIsNotInInternalDatabase() throws IOException {
        final JsonArray returnedGame = createTestJsonArray();
        doReturn(Optional.empty()).when(gameRepository).findGameById(anyLong());
        doReturn(returnedGame).when(igdbGateway).searchGameById(anyLong());

        Game returnGame = gamesService.searchGameById(id);

        assertThat(returnGame.getTitle()).isEqualTo("title");
        assertThat(returnGame.getReleases()).hasSize(2).extracting("platformName", "releaseDate").contains(tuple("IOS", now)).contains(tuple("PS5", now));
        assertThat(returnGame.getDevelopers()).hasSize(3).extracting("developer").contains(tuple("york")).contains(tuple("ethan")).contains(tuple("bruce"));
        assertThat(returnGame.getPublishers()).hasSize(3).extracting("publisherName").contains(tuple("york")).contains(tuple("ethan")).contains(tuple("bruce"));
        verify(gameRepository).create(any());
        verify(igdbGateway).searchGameById(id);
    }

    /**
     * @return private Long id;            // Game identifier.
     * private Integer version;    // Internal field for avoiding conflicts in optimistic locking.
     * @Column private String title;       // Name of the game. This value is unique.
     * @Column private String cover;       // String	URL of the cover of the game, or null if no cover.
     * private List<ReleaseDate> Releases;       // dates	ReleaseDate	One-to-many relationship of type ReleaseDate.
     * private List<Publisher> Publishers; //	Collection of Strings	One-to-many relationship between publishers and the name of the game.
     * private List<Developer> Developers;  // Collection of Strings	One-to-many relationship between developers and the name of the game.
     */
    private JsonArray createTestJsonArray() {
        JsonArrayBuilder releaseDateArray = Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("platform", "IOS").add("release_date", now).build())
                .add(Json.createObjectBuilder().add("platform", "PS5").add("release_date", now).build());

        JsonArrayBuilder developersArray = Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("developer", "york"))
                .add(Json.createObjectBuilder().add("developer", "ethan"))
                .add(Json.createObjectBuilder().add("developer", "bruce"));

        JsonArrayBuilder publishersArray = Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("publisherName", "york"))
                .add(Json.createObjectBuilder().add("publisherName", "ethan"))
                .add(Json.createObjectBuilder().add("publisherName", "bruce"));

        return Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("version", 1).build())
                .add(Json.createObjectBuilder().add("title", "title").build())
                .add(Json.createObjectBuilder().add("cover", "cover").build())
                .add(Json.createObjectBuilder().add("Releases", "cover").build())
                .add(Json.createObjectBuilder().add("publishers", publishersArray).build())
                .add(Json.createObjectBuilder().add("developers", developersArray).build())
                .add(Json.createObjectBuilder().add("releases", releaseDateArray).build())
                .build();
    }

}
