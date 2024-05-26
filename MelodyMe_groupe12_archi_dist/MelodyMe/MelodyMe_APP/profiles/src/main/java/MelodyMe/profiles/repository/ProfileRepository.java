package MelodyMe.profiles.repository;

import MelodyMe.profiles.entities.Badge;
import MelodyMe.profiles.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
