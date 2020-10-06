package tw.com.bruce.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080",
        "http://localhost:8181", "http://localhost:8282",
        "http://localhost:8383"})
@RestController
public class VideosResource {

    @Autowired
    private VideoServiceController videoServiceController;

    @RequestMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<String>> getVideos(@RequestParam("videoId") final long videoId,
                                                  @RequestParam("gameName") final String gameName) {
        final List<String> linksFromGame = videoServiceController.getLinksFromGame(Long.toString(videoId), gameName);
        return ResponseEntity.ok(linksFromGame);
    }
}
