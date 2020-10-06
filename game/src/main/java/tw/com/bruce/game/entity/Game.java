package tw.com.bruce.game.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class Game {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;            // Game identifier.

    @Version
    @Column(name = "version")
    private Integer version;    // Internal field for avoiding conflicts in optimistic locking.

    @Column
    private String title;       // Name of the game. This value is unique.

    @Column
    private String cover;       // String	URL of the cover of the game, or null if no cover.

    @ElementCollection
    @CollectionTable(name = "ReleaseDate", joinColumns = @JoinColumn(name = "OwnerId"))
    private List<ReleaseDate> Releases;       // dates	ReleaseDate	One-to-many relationship of type ReleaseDate.

    @ElementCollection
    @CollectionTable(name = "Publisher", joinColumns = @JoinColumn(name = "OwnerId"))
    private List<Publisher> Publishers; //	Collection of Strings	One-to-many relationship between publishers and the name of the game.

    @ElementCollection
    @CollectionTable(name = "Developer", joinColumns = @JoinColumn(name = "OwnerId"))
    private List<Developer> Developers;  // Collection of Strings	One-to-many relationship between developers and the name of the game.


    public static Game fromJson(JsonArray jsonByGameId) {
    }


    public JsonObject convertToJson() {
        final JsonArrayBuilder developers = Json.createArrayBuilder();
        this.getDevelopers().forEach(developers::add);

        final JsonArrayBuilder publishers = Json.createArrayBuilder();
        this.getPublishers().forEach(publishers::add);

        final JsonArrayBuilder releaseDates = Json.createArrayBuilder();

        this.getReleases().forEach(releaseDate -> {
            final String platform = releaseDate.getPlatformName();
            final String date = releaseDate.getReleaseDate().format(DateTimeFormatter.ISO_DATE);
            releaseDates.add(Json.createObjectBuilder().add("platform", platform).add("release_date", date));
        });

        return Json.createObjectBuilder()
                .add("id", this.getId())
                .add("title", this.getTitle())
                .add("cover", this.getCover())
                .add("developers", developers)
                .add("publishers", publishers)
                .add("release_dates", releaseDates)
                .build();
    }
}
