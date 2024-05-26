package music.microservice.Controller;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import music.microservice.Service.SpotifyService;
import java.io.IOException;

@RestController
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/search")
    public Paging<Track> searchTracks(@RequestParam String query) {
        try {
            return spotifyService.searchTracks(query);
        } catch (SpotifyWebApiException | IOException e) {
            // Gérer les exceptions
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

 

}
