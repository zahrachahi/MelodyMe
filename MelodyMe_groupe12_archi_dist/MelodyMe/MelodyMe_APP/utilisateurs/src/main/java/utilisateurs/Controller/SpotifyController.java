package utilisateurs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;
@RestController
public class SpotifyController {

    private static final Logger logger = Logger.getLogger(SpotifyController.class.getName());

    private final String clientId = "246cbb4fb7424113a01b53a58deb32b3";
    private final String clientSecret = "ec75269617f349c4875e157df45d3c39";
    private final String redirectUri = "http://localhost:8080/spotify/callback";
    private final String scope = "user-read-private user-read-email";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/spotify/login")
    public RedirectView spotifyLogin() {
        String uri = UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .toUriString();
        logger.info("Redirecting to Spotify authorization URL: " + uri);
        return new RedirectView(uri);
    }

    @GetMapping("/spotify/callback")
    public ResponseEntity<String> spotifyCallback(@RequestParam("code") String code) {
        logger.info("Received callback with code: " + code);
        String uri = UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/api/token")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            logger.info("Received response from Spotify token endpoint: " + responseBody);
            if (responseBody != null && responseBody.containsKey("access_token")) {
                String accessToken = (String) responseBody.get("access_token");
                // Récupérer les données utilisateur avec le token
                return getUserData(accessToken);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pas de token d'accès dans la réponse");
            }
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Erreur lors de la récupération du token");
        }
    }

    public ResponseEntity<String> getUserData(String accessToken) {
        String uri = "https://api.spotify.com/v1/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("User data retrieved successfully");
            return ResponseEntity.ok("Données utilisateur : " + response.getBody());
        } else {
            logger.severe("Error retrieving user data: " + response.getStatusCode());
            return ResponseEntity.status(response.getStatusCode()).body("Erreur lors de la récupération des données utilisateur");
        }
    }
}
