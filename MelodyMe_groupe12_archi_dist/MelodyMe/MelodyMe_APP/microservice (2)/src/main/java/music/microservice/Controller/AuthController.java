package music.microservice.Controller;

import music.microservice.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/callback")
    public String handleCallback(@RequestParam("code") String code) {
//pour obtenir un jeton d'accès
        String accessToken = authService.getAccessToken(code);
        //  jeton d'accès pour accéder aux API Spotify
        return "Access token: " + accessToken;
    }


}
