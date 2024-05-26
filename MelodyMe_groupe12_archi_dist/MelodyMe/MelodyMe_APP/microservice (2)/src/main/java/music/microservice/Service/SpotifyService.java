package music.microservice.Service;

import music.microservice.Entity.Musique;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {

    @Autowired
    private SpotifyApi spotifyApi;

    public Paging<Track> searchTracks(String query) throws SpotifyWebApiException, IOException, ParseException {
        // Obtenir le jeton d'accès OAuth2 directement
        String accessToken = spotifyApi.clientCredentials().build().execute().getAccessToken();

        // Configuration de l'API Spotify avec le jeton d'accès
        spotifyApi.setAccessToken(accessToken);
        System.out.println(accessToken);

        // Recherche de pistes Spotify
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(query).build();
        return searchTracksRequest.execute();
    }

    public User getProfile(String accessToken) throws SpotifyWebApiException, IOException, ParseException {
        // Configurez l'API Spotify avec le jeton d'accès
        System.out.println("Access token: " + spotifyApi.getAccessToken()); // Imprimer le jeton d'accès

        // Obtenez les informations du profil utilisateur actuellement authentifié
        GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();
        try {
            User user = request.execute();
            System.out.println("User profile: " + user); // Imprimer le profil de l'utilisateur
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Répétez l'exception pour la gérer dans le contrôleur
        }
    }

    // Récupérez les musique sauvegardés par l'utilisateur

    // Méthode pour récupérer les playlists de l'utilisateur avec un jeton d'accès
    public Paging<PlaylistSimplified> getUserPlaylists(String accessToken)
            throws SpotifyWebApiException, IOException, ParseException {
        // Configurez l'API Spotify avec le jeton d'accès fourni
        spotifyApi.setAccessToken(accessToken);

        // Créez une demande pour obtenir les listes de lecture de l'utilisateur
        // actuellement authentifié
        GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists().build();

        // Exécutez la demande et retournez la réponse
        return request.execute();
    }

    // Méthode pour récupérer les playlists de l'utilisateur sans un jeton d'accès
    // explicite (à utiliser avec prudence)
    public Paging<PlaylistSimplified> getUserPlaylists() throws SpotifyWebApiException, IOException, ParseException {
        // Assurez-vous que l'API Spotify est configurée avec un jeton d'accès valide
        if (spotifyApi.getAccessToken() == null || spotifyApi.getAccessToken().isEmpty()) {
            throw new IllegalStateException("Access token is missing or invalid");
        }

        // Créez une demande pour obtenir les listes de lecture de l'utilisateur
        // actuellement authentifié
        GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists().build();

        // Exécutez la demande et retournez la réponse
        return request.execute();
    }

    // Recuperer payiste piste


     public List<Musique> getTracksFromUserPlaylists(String accessToken) throws SpotifyWebApiException, IOException, ParseException {
        spotifyApi.setAccessToken(accessToken);
        List<Musique> musiques = new ArrayList<>();
        GetListOfCurrentUsersPlaylistsRequest playlistsRequest = spotifyApi.getListOfCurrentUsersPlaylists().build();
        Paging<PlaylistSimplified> playlists = playlistsRequest.execute();

        for (PlaylistSimplified playlist : playlists.getItems()) {
            GetPlaylistsItemsRequest playlistItemsRequest = spotifyApi.getPlaylistsItems(playlist.getId()).build();
            Paging<PlaylistTrack> playlistTracks = playlistItemsRequest.execute();

            for (PlaylistTrack playlistTrack : playlistTracks.getItems()) {
                Track track = (Track) playlistTrack.getTrack();
                Musique musique = new Musique();
                musique.setTitre(track.getName());
                musique.setArtiste(track.getArtists()[0].getName());
                musique.setGenre(track.getAlbum().getName() != null ? track.getAlbum().getName() : "Unknown");
                // Assurez-vous de définir la relation avec la playlist si nécessaire
                musiques.add(musique);
            }
        }

        return musiques;
    }

}
