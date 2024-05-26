package music.microservice.Entity;
import jakarta.persistence.*;
@Entity
@Table(name = "Profil")
public class Profil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ProfilId")
    private Long id;

    @Column(name = "Description")
    private String description;

    @Column(name = "Email")
    private String email;



    // Ajoutez ici les getters et setters pour les nouveaux champs

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}