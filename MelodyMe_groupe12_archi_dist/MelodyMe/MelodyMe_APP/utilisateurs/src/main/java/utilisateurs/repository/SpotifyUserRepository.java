package utilisateurs.repository;

import utilisateurs.entities.SpotifyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyUserRepository extends JpaRepository<SpotifyUser, String> {
}
