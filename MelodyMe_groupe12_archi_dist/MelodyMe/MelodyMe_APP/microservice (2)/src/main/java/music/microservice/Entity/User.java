package music.microservice.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"User\"") // Échapper le nom de la table en utilisant des guillemets doubles
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserId")
    private Long id;

    // ajouter d'autres champs liés à l'utilisateur si nécessaire

    // getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
