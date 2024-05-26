package MelodyMe.profiles.Controller;

import MelodyMe.profiles.entities.Badge;
import MelodyMe.profiles.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/badges")
public class BadgeController {

    @Autowired
    private BadgeRepository badgeRepository;

    @GetMapping
    public ResponseEntity<List<Badge>> getAllBadges() {
        List<Badge> badges = badgeRepository.findAll();
        return new ResponseEntity<>(badges, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadgeById(@PathVariable Long id) {
        Optional<Badge> badgeOptional = badgeRepository.findById(id);
        return badgeOptional.map(badge -> new ResponseEntity<>(badge, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Badge> createBadge(@RequestBody Badge badge) {
        Badge createdBadge = badgeRepository.save(badge);
        return new ResponseEntity<>(createdBadge, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Badge> updateBadge(@PathVariable Long id, @RequestBody Badge updatedBadge) {
        Optional<Badge> badgeOptional = badgeRepository.findById(id);
        if (badgeOptional.isPresent()) {
            updatedBadge.setBadgeId(id);
            Badge savedBadge = badgeRepository.save(updatedBadge);
            return new ResponseEntity<>(savedBadge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBadge(@PathVariable Long id) {
        badgeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}