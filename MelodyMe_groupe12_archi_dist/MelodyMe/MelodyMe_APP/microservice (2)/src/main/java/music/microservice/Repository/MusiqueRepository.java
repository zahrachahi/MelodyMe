package music.microservice.Repository;


import music.microservice.Entity.Musique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusiqueRepository extends JpaRepository<Musique, Long> {

}
