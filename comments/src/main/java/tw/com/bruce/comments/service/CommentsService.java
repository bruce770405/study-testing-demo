package tw.com.bruce.comments.service;

public interface CommentsService {

    Optional<Document> getCommentsAndRating(Integer gameId);
}
