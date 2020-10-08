package tw.com.bruce.video.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class YouTubeLinkTest {

    private YouTubeLink youtubeLink;

    private YouTubeVideoLinkCreator youTubeVideoLinkCreator;

    @BeforeEach
    public void setUp() {
        youtubeLink = new YouTubeLink("1234");
        youTubeVideoLinkCreator = new YouTubeVideoLinkCreator();
    }

    /**
     * test link: https://www.youtube.com/embed/1234
     */
    @Test
    void shouldCalculateEmbedYouTubeLink() {
        youtubeLink.setYouTubeVideoLinkCreator(youTubeVideoLinkCreator::createEmbeddedUrl);
        assertThat(youtubeLink.getEmbedUrl()).hasHost("www.youtube.com").hasPath("/embed/1234");
    }
}
