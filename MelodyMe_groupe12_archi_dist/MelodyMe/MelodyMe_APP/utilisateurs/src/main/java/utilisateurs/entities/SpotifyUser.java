package utilisateurs.entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity()
@Table(name = "spotifyuser")
public class SpotifyUser {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "href")
    private String href;

    @Column(name = "uri")
    private String uri;

    @Column(name = "spotify_url", length = 2048)
    private String spotifyUrl;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }
}
