package fr.intellcap.backend.controller;

import fr.intellcap.backend.dto.PartnerRequestDTO;
import fr.intellcap.backend.entity.PartnerRequest;
import fr.intellcap.backend.service.EmailService;
import fr.intellcap.backend.service.PartnerRequestService;
import fr.intellcap.backend.service.RecaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/partner-requests")
public class PartnerRequestController {

    private final PartnerRequestService partnerRequestService;
    private final RecaptchaService recaptchaService;
    private final EmailService emailService;

    //  Rate limiting par IP — RG-05 (5 req/min par IP)
    private final Map<String, Instant> lastSubmissionByIp = new ConcurrentHashMap<>();

    public PartnerRequestController(PartnerRequestService partnerRequestService,
                                    RecaptchaService recaptchaService,
                                    EmailService emailService) {
        this.partnerRequestService = partnerRequestService;
        this.recaptchaService = recaptchaService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> submitPartnerRequest(
            @Valid @RequestBody PartnerRequestDTO dto,
            HttpServletRequest request) {  //  injection de la requête pour lire l'IP

        // ── reCAPTCHA ──────────────────────────────────────────────────
        // if (!recaptchaService.verifyToken(dto.getRecaptchaToken())) {
//     return ResponseEntity.badRequest()
//             .body(Map.of("error", "Vérification reCAPTCHA échouée."));
// }

        // ── Rate limiting par IP — RG-05 ───────────────────────────────
        String clientIp = getClientIp(request);
        Instant now = Instant.now();
        Instant last = lastSubmissionByIp.get(clientIp);

        if (last != null && now.isBefore(last.plusSeconds(60))) {
            //  1 soumission par minute par IP (CDC : 5 req/min max)
            return ResponseEntity.status(429)
                    .body(Map.of(
                        "error", "Trop de requêtes. Réessayez dans 1 minute.",
                        "status", 429
                    ));
        }
        lastSubmissionByIp.put(clientIp, now);

        // ── Sauvegarde ─────────────────────────────────────────────────
        PartnerRequest saved = partnerRequestService.createFromDTO(dto);

        // ── Notification email ─────────────────────────────────────────
        try {
            emailService.sendPartnerRequestNotification(saved);
        } catch (Exception e) {
            System.err.println("Erreur envoi email: " + e.getMessage());
        }

        return ResponseEntity.ok(Map.of(
                "message", "Demande envoyée avec succès.",
                "id", saved.getId()
        ));
    }

    //  Lit l'IP réelle même derrière un proxy/load balancer
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}