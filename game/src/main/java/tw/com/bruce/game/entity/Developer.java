package tw.com.bruce.game.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Developer {

    @Id
    private String OwnerId;    // Long	Identifier for the game. This field acts as a foreign key.
    private String developer; //	String	Developer name.
}
