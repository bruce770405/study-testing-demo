package tw.com.bruce.comments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.bruce.comments.service.CommentsService;

@RequestMapping
@RestController
public class CommentController {

    @Autowired
    private CommentsService comments;

    @Autowired
    private DocumentToJsonObject transformer;

    @GetMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCommentsOfGivenGame(@PathVariable("gameId") final Integer gameId) {
        final Optional<Document> commentsAndRating = comments.getCommentsAndRating(gameId);
        final JsonObject json = transformer.transform(commentsAndRating.orElse(new Document()));
        return ResponseEntity.ok(json);
    }

}
