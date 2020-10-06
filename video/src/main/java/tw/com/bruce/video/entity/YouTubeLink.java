package tw.com.bruce.video.entity;

import java.net.URL;
import java.util.function.Function;

public class YouTubeLink {

    private final String videoId;
    private Function<String, URL> youTubeVideoLinkCreator;

    public YouTubeLink(final String videoId) {
        this.videoId = videoId;
    }

    public void setYouTubeVideoLinkCreator(final Function<String, URL> youTubeVideoLinkCreator) {
        this.youTubeVideoLinkCreator = youTubeVideoLinkCreator;
    }

    public URL getEmbedUrl() {
        if (youTubeVideoLinkCreator != null) {
            return youTubeVideoLinkCreator.apply(this.videoId);
        } else {
            throw new IllegalStateException("YouTubeVideoLinkCreator not set");
        }
    }

    public String getVideoId() {
        return videoId;
    }
}
