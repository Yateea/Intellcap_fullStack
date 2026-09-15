package fr.intellcap.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/talents")
public class TalentRedirectController {

    @GetMapping("/platforms")
    public ResponseEntity<?> getExternalPlatforms() {
        return ResponseEntity.ok(List.of(
            Map.of("name", "Elev8 Careers",
                   "description", "Rejoignez le programme d'accélération de startups Deep Tech",
                   "url", "https://elev8.intellcap.fr/careers",
                   "type", "STARTUP_ACCELERATOR"),
            Map.of("name", "InnovDays Talent",
                   "description", "Opportunités lors des événements d'innovation internationaux",
                   "url", "https://innovdays.intellcap.fr/join",
                   "type", "EVENTS_NETWORKING"),
            Map.of("name", "Constellium Partners",
                   "description", "Intégrez le réseau de partenaires stratégiques et investisseurs",
                   "url", "https://constellium.intellcap.fr/opportunities",
                   "type", "PARTNER_NETWORK"),
            Map.of("name", "INTELLCAP Startups",
                   "description", "Portfolio et incubation de startups technologiques",
                   "url", "https://startups.intellcap.fr/hiring",
                   "type", "STARTUP_PORTFOLIO")
        ));
    }

    @GetMapping("/redirect")
    public ResponseEntity<?> redirectToMainTalentPlatform() {
        return ResponseEntity.ok(Map.of(
            "message", "Redirection vers la plateforme de talents INTELLCAP",
            "externalUrl", "https://talents.intellcap.fr",
            "note", "Aucune candidature n'est gérée sur le site principal."
        ));
    }
}