package music.microservice.Controller;

import music.microservice.Repository.ProfilRepository;
import music.microservice.Entity.Profil;
import music.microservice.Service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ProfileController {

   @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private ProfilRepository profilRepository;

    @GetMapping("/profile")
    public String getProfile(@RequestParam("accessToken") String accessToken) {
        try {
            User user = spotifyService.getProfile(accessToken);
            

            // Stockez les informations du profil dans votre entité Profil
            Profil profil = new Profil();
            profil.setEmail(user.getEmail());
            // Enregistrez le profil dans la base de données
            profilRepository.save(profil);
            return "Profile saved successfully";
        } catch (SpotifyWebApiException | IOException | org.apache.hc.core5.http.ParseException e) {
            e.printStackTrace();
            return "Error fetching profile";
        }
    }
    @PutMapping("/profile/{profileId}")
    public String updateProfile(@RequestParam("accessToken") String accessToken, @PathVariable Long profileId, @RequestBody Profil profilToUpdate) {
        try {
            // Vérifiez si le profil existe déjà dans la base de données
            Optional<Profil> optionalProfil = profilRepository.findById(profileId);
            if (optionalProfil.isPresent()) {
                Profil existingProfil = optionalProfil.get(); // Si le profil existe déjà, récupérez-le

                // Modifiez les informations du profil
                existingProfil.setEmail(profilToUpdate.getEmail());
                existingProfil.setDescription(profilToUpdate.getDescription());

                // Enregistrez les modifications dans la base de données
                profilRepository.save(existingProfil);

                return "Profile updated successfully";
            } else {
                return "Profile not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating profile";
        }
    }




}
