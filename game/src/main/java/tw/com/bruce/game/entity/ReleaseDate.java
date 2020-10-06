package tw.com.bruce.game.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ReleaseDate {

    @Id
    private Long OwnerId;  // Long Identifier for the game. This field acts as a foreign key.
    private String platformName; // String	Platform name under which game was released.
    private String releaseDate;    // String	Date when the game was released for this platform, in YYYY/MM/DD format.
}
