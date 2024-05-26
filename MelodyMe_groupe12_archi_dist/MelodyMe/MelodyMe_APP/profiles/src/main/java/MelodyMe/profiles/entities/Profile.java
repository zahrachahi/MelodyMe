package MelodyMe.profiles.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profilId;

    @Column(name = "biography", nullable = true)
    private String biography;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "orientation", nullable = false)
    private Orientation orientation;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation", nullable = false)
    private Situation situation;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // Constructors
    public Profile() {}

    public Profile(Long profilId, String biography, Gender gender, Orientation orientation, Situation situation, User user, Set<Badge> badges) {
        this.profilId = profilId;
        this.biography = biography;
        this.gender = gender;
        this.orientation = orientation;
        this.situation = situation;
        this.user = user;
    }

    // Getters and Setters
    public Long getProfilId() {
        return profilId;
    }

    public void setProfilId(Long profilId) {
        this.profilId = profilId;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
