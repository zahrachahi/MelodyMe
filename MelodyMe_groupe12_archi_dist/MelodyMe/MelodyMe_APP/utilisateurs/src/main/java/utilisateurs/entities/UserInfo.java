package utilisateurs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_info") // Utilisez snake_case pour le nom de la table
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_name") // Utilisez snake_case pour le nom des colonnes
    private String displayName;

    @Column(name = "email")
    private String email;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
