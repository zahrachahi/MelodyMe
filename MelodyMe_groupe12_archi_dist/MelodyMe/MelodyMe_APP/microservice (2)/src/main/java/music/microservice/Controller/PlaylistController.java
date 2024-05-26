package music.microservice.Controller;

import music.microservice.Repository.PlaylistRepository;
import music.microservice.Entity.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.List;
import java.util.Optional;

import music.microservice.Service.SpotifyService;

@RestController
public class PlaylistController {

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private PlaylistRepository playlistRepository; // Assurez-vous d'avoir une interface PlaylistRepository pour interagir avec la base de données

    /**
     * @param accessToken
     * @return
     */
    @GetMapping("/user/playlists")
    public ResponseEntity<String> getUserPlaylists(@RequestParam("accessToken") String accessToken) {
        try {
            // Récupérer les playlists de l'utilisateur à partir du service Spotify
            Paging<PlaylistSimplified> playlistsPaging = spotifyService.getUserPlaylists(accessToken);
            PlaylistSimplified[] playlistSimplifiedList = playlistsPaging.getItems();

            // Enregistrer les playlists dans la base de données
            for (PlaylistSimplified playlistSimplified : playlistSimplifiedList) {
                Playlist playlist = new Playlist();
                playlist.setName(playlistSimplified.getName());
                playlist.setDescription(playlistSimplified.getSnapshotId()); // Ajouter la description de la playlist
                playlistRepository.save(playlist); // Enregistrer la playlist dans la base de données
            }

            // Réponse réussie avec un message indiquant que les playlists ont été récupérées et enregistrées
            return ResponseEntity.ok("Playlists récupérées et enregistrées avec succès dans la base de données.");
        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'erreur, retourner une réponse avec un code d'erreur 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la récupération ou de l'enregistrement des playlists.");
        }
    }



    @GetMapping("/user/playlists/all")
public ResponseEntity<String> getPlaylists(@RequestParam("accessToken") String accessToken) {
    try {
        // Vérifier si l'accessToken est fourni
        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.badRequest().body("L'accessToken est requis.");
        }

        // Si l'accessToken est fourni, récupérer et renvoyer toutes les playlists de la base de données
        List<Playlist> playlists = playlistRepository.findAll();

        // Convertir la liste de playlists en format JSON
        ObjectMapper mapper = new ObjectMapper();
        String playlistsJson = mapper.writeValueAsString(playlists);

        // Renvoyer la réponse avec les playlists au format JSON
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(playlistsJson);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la récupération des playlists.");
    }
}


    
    


    @GetMapping("/user/playlists/all/{playlistId}")
    public ResponseEntity<String> getPlaylistsById(@PathVariable Long playlistId, @RequestParam("accessToken") String accessToken) {
        try {
            // Vérifier si l'accessToken est fourni
            if (accessToken == null || accessToken.isEmpty()) {
                return ResponseEntity.badRequest().body("L'accessToken est requis.");
            }

            // Si l'accessToken est fourni, récupérer la playlist à partir de son identifiant
            Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);

            // Vérifier si la playlist existe
            if (optionalPlaylist.isPresent()) {
                // Playlist trouvée, renvoyer ses données au format JSON
                Playlist playlist = optionalPlaylist.get();
                ObjectMapper objectMapper = new ObjectMapper();
                String playlistJson = objectMapper.writeValueAsString(playlist);
                return ResponseEntity.ok(playlistJson);
            } else {
                // Aucune playlist trouvée avec l'identifiant spécifié, renvoyer une réponse d'erreur 404 Not Found
                return ResponseEntity.notFound().build();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la conversion de la playlist en JSON.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la récupération de la playlist.");
        }
    }

    @PutMapping("/update/{playlistId}")
    public ResponseEntity<String> updatePlaylistById(@PathVariable Long playlistId, @RequestParam("accessToken") String accessToken, @RequestBody Playlist newPlaylistData) {
        try {
            // Vérifier si l'accessToken est fourni
            if (accessToken == null || accessToken.isEmpty()) {
                return ResponseEntity.badRequest().body("L'accessToken est requis.");
            }

            // Récupérer la playlist à partir de son identifiant
            Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);

            // Vérifier si la playlist existe
            if (optionalPlaylist.isPresent()) {
                // Mettre à jour les données de la playlist avec les nouvelles données
                Playlist existingPlaylist = optionalPlaylist.get();
                existingPlaylist.setName(newPlaylistData.getName());
                existingPlaylist.setDescription(newPlaylistData.getDescription()); // Mettre à jour la description si nécessaire

                // Enregistrer les modifications dans la base de données
                playlistRepository.save(existingPlaylist);

                // Retourner une réponse indiquant que la playlist a été mise à jour avec succès
                return ResponseEntity.ok("Playlist mise à jour avec succès.");
            } else {
                // Aucune playlist trouvée avec l'identifiant spécifié, renvoyer une réponse d'erreur 404 Not Found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'erreur, retourner une réponse avec un code d'erreur 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la mise à jour de la playlist.");
        }
    }





    @DeleteMapping("/user/playlists/delete/{playlistId}")
public ResponseEntity<String> deletePlaylistById(@PathVariable Long playlistId, @RequestParam("accessToken") String accessToken) {
    try {
        // Vérifier si l'accessToken est fourni
        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.badRequest().body("L'accessToken est requis.");
        }

        // Récupérer la playlist à partir de son identifiant
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);

        // Vérifier si la playlist existe
        if (optionalPlaylist.isPresent()) {
            // Supprimer la playlist
            playlistRepository.deleteById(playlistId);

            // Retourner une réponse indiquant que la playlist a été supprimée avec succès
            return ResponseEntity.ok("Playlist supprimée avec succès.");
        } else {
            // Aucune playlist trouvée avec l'identifiant spécifié, renvoyer une réponse d'erreur 404 Not Found
            return ResponseEntity.notFound().build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        // En cas d'erreur, retourner une réponse avec un code d'erreur 500 (Internal Server Error)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la suppression de la playlist.");
    }
}

    
}
