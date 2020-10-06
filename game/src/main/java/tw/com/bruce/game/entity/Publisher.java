package tw.com.bruce.game.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Publisher {

    @Id
    private String OwnerId;    //Long	Identifier for the game. This field acts as a foreign key.
    private String publisherName; //	String	Publisher name.
}
