package tw.com.bruce.comments.entity;

import lombok.Data;

@Data
public class Comment {
    private String gameId;  // "gameId": 1234,
    private String comment; // "comment": "This game is awesome",
    private Integer rate;   // "rate": 3
}
