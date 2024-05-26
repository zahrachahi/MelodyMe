package music.microservice.Controller;



import java.io.IOException;
import java.util.List;
import music.microservice.Repository.MusiqueRepository;
import music.microservice.Entity.Musique;
import music.microservice.Service.SpotifyService;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

//par suite cree un build en utilisant mvn clean package
@RestController
@RequestMapping("/api/musiques")
public class MusiqueController {


    @Autowired
    private MusiqueRepository musiqueRepository;   

    @Autowired
    private SpotifyService spotifyService;
    @GetMapping("/user/tracks")
    public ResponseEntity<List<Musique>> getTracksFromUserPlaylists(@RequestParam("accessToken") String accessToken) {
        try {
            List<Musique> tracks = spotifyService.getTracksFromUserPlaylists(accessToken);
            musiqueRepository.saveAll(tracks);
            return ResponseEntity.ok(tracks);
        } catch (SpotifyWebApiException | IOException | ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping
    public List<Musique> getAllMusiques(){
        return musiqueRepository.findAll();
    }

    @GetMapping("/{id}")
    public Musique getMusiqueById(@PathVariable Long id){
        return musiqueRepository.findById(id).get();
    }

    @PostMapping
    public Musique createMusique(@RequestBody Musique musique){
        return musiqueRepository.save(musique);
    }
    @PutMapping("/{id}")
    public Musique updateMusique(@PathVariable Long id, @RequestBody Musique musique){

        Musique existingMusique= musiqueRepository.findById(id).get();
        existingMusique.setArtiste(musique.getArtiste());
        existingMusique.setGenre(musique.getGenre());
        existingMusique.setTitre(musique.getTitre());
        return musiqueRepository.save(existingMusique);
    };
    @DeleteMapping("/{id}")
    public String deleteMusique(@PathVariable Long id){
        try {
            musiqueRepository.findById(id).get();
            musiqueRepository.deleteById(id);
            return "musique deleted successfully";

        }catch (Exception e){
            return "musique not found";
        }
    }




 /*   @PostMapping("/import")
    public String importUserSavedTracks(@RequestParam("accessToken") String accessToken) {
        try {
            // Récupérez les morceaux sauvegardés par l'utilisateur
            Paging<SavedTrack> savedTracks = spotifyService.getUserSavedTracks(accessToken);

            // Bouclez à travers les pistes récupérées et stockez-les dans la base de données
            for (SavedTrack savedTrack : savedTracks.getItems()) {
                Track track = savedTrack.getTrack();
                Musique musique = new Musique();
                musique.setTitre(track.getName());
                musique.setArtiste(track.getArtists()[0].getName());
                musiqueRepository.save(musique);
            }

            return "Importation réussie des musiques sauvegardées de l'utilisateur";
        } catch (SpotifyWebApiException | IOException | ParseException e) {
            e.printStackTrace();
            return "Erreur lors de l'importation des musiques sauvegardées de l'utilisateur";
        }
    }
*/



}
