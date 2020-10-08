package tw.com.bruce.video.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class YouTubeVideoLinkCreatorTest {

    private YouTubeVideoLinkCreator youTubeVideoLinkCreator;

    @BeforeEach
    public void setUp() {
        youTubeVideoLinkCreator = new YouTubeVideoLinkCreator();
    }

    @Test
    void shouldReturnYouTubeEmbeddedUrlForGivenVideoId() {
        final URL embeddedUrl = youTubeVideoLinkCreator.createEmbeddedUrl("1234");
        assertThat(embeddedUrl).hasHost("www.youtube.com").hasPath("/embed/1234");

    }
}
