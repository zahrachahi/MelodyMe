package MelodyMe.profiles.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "badge")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long badge_id;

    @Column(name = "badge_name")
    private String badgeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profileId;


    public Badge(String badgeName, Profile profile) {
        this.badgeName = badgeName;
        this.profileId = profile;
    }

    public Badge() {

    }

    // Getters and Setters
    public Long getBadgeId() {
        return badge_id;
    }

    public void setBadgeId(Long badgeId) {
        this.badge_id = badgeId;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }
    public Profile getProfile() {
        return profileId;
    }
    public void setProfile(Profile profile) {
        this.profileId = profile;
    }

}
