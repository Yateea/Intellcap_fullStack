package fr.intellcap.backend.controller;

import fr.intellcap.backend.entity.*;
import fr.intellcap.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final PartnerRequestRepository partnerRequestRepository;
    private final FeaturedProjectRepository featuredProjectRepository;
    private final EcosystemProgramRepository ecosystemProgramRepository;
    private final PostRepository postRepository;
    private final ConsultingServiceRepository consultingServiceRepository;
    private final LocationRepository locationRepository;

    // ── GESTION USERS ──────────────────────────────

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        var list = userRepository.findAll().stream()
                .map(user -> {
                    var map = new HashMap<String, Object>();
                    map.put("id", user.getId());
                    map.put("fullName", user.getFullName());
                    map.put("email", user.getEmail());
                    map.put("role", user.getRole());
                    map.put("active", user.isActive());
                    map.put("createdAt", user.getCreatedAt());
                    return map;
                }).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            user.setActive(true);
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "Compte activé.", "id", id));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}/suspend")
    public ResponseEntity<?> suspendUser(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            user.setActive(false);
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "Compte suspendu.", "id", id));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id))
            return ResponseEntity.notFound().build();
        userRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Compte supprimé.", "id", id));
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> changeRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return userRepository.findById(id).map(user -> {
            try {
                User.Role newRole = User.Role.valueOf(body.get("role"));
                user.setRole(newRole);
                userRepository.save(user);
                return ResponseEntity.ok(Map.of(
                        "message", "Rôle mis à jour.",
                        "id", id, "role", newRole));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Rôle invalide. Valeurs acceptées: USER, ADMIN"));
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    // ── ANALYTICS ──────────────────────────────────

    @GetMapping("/analytics")
    public ResponseEntity<?> getAnalytics() {
        var stats = new HashMap<String, Object>();

        // Utilisateurs
        stats.put("totalUsers", userRepository.count());
        stats.put("totalAdmins", userRepository.findAll().stream()
                .filter(u -> u.getRole() == User.Role.ADMIN).count());

        // Partenaires
        stats.put("totalPartnerRequests", partnerRequestRepository.count());

        // Projets
        stats.put("totalProjects", featuredProjectRepository.count());
        stats.put("activeProjects", featuredProjectRepository.findByActiveTrue().size());

        // Posts — ✅ stats demande client point 1
        stats.put("totalPosts", postRepository.count());
        stats.put("publishedPosts",
                postRepository.findByPublishedTrueOrderByCreatedAtDesc().size());
        stats.put("totalViews", postRepository.findAll().stream()
                .mapToLong(p -> p.getViewCount() != null ? p.getViewCount() : 0L)
                .sum());
        stats.put("totalDownloads", postRepository.findAll().stream()
                .mapToLong(p -> p.getDownloadCount() != null ? p.getDownloadCount() : 0L)
                .sum());

        // Consulting
        stats.put("totalConsultingServices", consultingServiceRepository.count());
        stats.put("activeConsultingServices",
                consultingServiceRepository.findByActiveTrue().size());

        // Localisations
        stats.put("totalLocations", locationRepository.count());

        return ResponseEntity.ok(stats);
    }

    // ── MODÉRATION PROJETS PHARES ───────────────────

    @GetMapping("/projects")
    public ResponseEntity<?> getProjects() {
        return ResponseEntity.ok(featuredProjectRepository.findAll());
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestBody FeaturedProject project) {
        return ResponseEntity.ok(featuredProjectRepository.save(project));
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<?> updateProject(
            @PathVariable Long id,
            @RequestBody FeaturedProject updated) {
        return featuredProjectRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setDomain(updated.getDomain());
            p.setDescription(updated.getDescription());
            p.setLogoUrl(updated.getLogoUrl());
            return ResponseEntity.ok(featuredProjectRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/projects/{id}/toggle")
    public ResponseEntity<?> toggleProject(@PathVariable Long id) {
        return featuredProjectRepository.findById(id).map(p -> {
            p.setActive(!p.isActive());
            featuredProjectRepository.save(p);
            return ResponseEntity.ok(Map.of(
                    "id", id, "active", p.isActive(),
                    "message", p.isActive() ? "Activé." : "Désactivé."));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ── MODÉRATION POSTS ────────────────────────────

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postRepository.findAll());
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        return ResponseEntity.ok(postRepository.save(post));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody Post updated) {
        return postRepository.findById(id).map(p -> {
            p.setTitle(updated.getTitle());
            p.setContent(updated.getContent());
            p.setCategory(updated.getCategory());
            p.setAuthor(updated.getAuthor());
            p.setImageUrl(updated.getImageUrl());
            return ResponseEntity.ok(postRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/posts/{id}/publish")
    public ResponseEntity<?> publishPost(@PathVariable Long id) {
        return postRepository.findById(id).map(p -> {
            p.setPublished(true);
            postRepository.save(p);
            return ResponseEntity.ok(Map.of("id", id, "message", "Post publié."));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/posts/{id}/unpublish")
    public ResponseEntity<?> unpublishPost(@PathVariable Long id) {
        return postRepository.findById(id).map(p -> {
            p.setPublished(false);
            postRepository.save(p);
            return ResponseEntity.ok(Map.of("id", id, "message", "Post dépublié."));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id))
            return ResponseEntity.notFound().build();
        postRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Post supprimé.", "id", id));
    }

    // ── MODÉRATION CONSULTING ───────────────────────

    @GetMapping("/consulting")
    public ResponseEntity<?> getAllConsulting() {
        return ResponseEntity.ok(consultingServiceRepository.findAll());
    }

    @PostMapping("/consulting")
    public ResponseEntity<?> createConsulting(@RequestBody ConsultingService service) {
        return ResponseEntity.ok(consultingServiceRepository.save(service));
    }

    @PutMapping("/consulting/{id}")
    public ResponseEntity<?> updateConsulting(
            @PathVariable Long id,
            @RequestBody ConsultingService updated) {
        return consultingServiceRepository.findById(id).map(s -> {
            s.setTitle(updated.getTitle());
            s.setDescription(updated.getDescription());
            s.setDomain(updated.getDomain());
            s.setIconUrl(updated.getIconUrl());
            return ResponseEntity.ok(consultingServiceRepository.save(s));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/consulting/{id}/toggle")
    public ResponseEntity<?> toggleConsulting(@PathVariable Long id) {
        return consultingServiceRepository.findById(id).map(s -> {
            s.setActive(!s.isActive());
            consultingServiceRepository.save(s);
            return ResponseEntity.ok(Map.of("id", id, "active", s.isActive()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/consulting/{id}")
    public ResponseEntity<?> deleteConsulting(@PathVariable Long id) {
        if (!consultingServiceRepository.existsById(id))
            return ResponseEntity.notFound().build();
        consultingServiceRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Service supprimé.", "id", id));
    }

    // ── MODÉRATION LOCALISATIONS ────────────────────

    @GetMapping("/locations")
    public ResponseEntity<?> getAllLocations() {
        return ResponseEntity.ok(locationRepository.findAll());
    }

    @PostMapping("/locations")
    public ResponseEntity<?> createLocation(@RequestBody Location location) {
        return ResponseEntity.ok(locationRepository.save(location));
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<?> updateLocation(
            @PathVariable Long id,
            @RequestBody Location updated) {
        return locationRepository.findById(id).map(l -> {
            l.setCountry(updated.getCountry());
            l.setCity(updated.getCity());
            l.setAddress(updated.getAddress());
            l.setLatitude(updated.getLatitude());
            l.setLongitude(updated.getLongitude());
            l.setCountryCode(updated.getCountryCode());
            l.setPhone(updated.getPhone());
            l.setEmail(updated.getEmail());
            return ResponseEntity.ok(locationRepository.save(l));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        if (!locationRepository.existsById(id))
            return ResponseEntity.notFound().build();
        locationRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Localisation supprimée.", "id", id));
    }

    // ── MODÉRATION ECOSYSTEM ────────────────────────

    @GetMapping("/ecosystem")
    public ResponseEntity<?> getAllPrograms() {
        return ResponseEntity.ok(ecosystemProgramRepository.findAll());
    }

    @PostMapping("/ecosystem")
    public ResponseEntity<?> createProgram(@RequestBody EcosystemProgram program) {
        return ResponseEntity.ok(ecosystemProgramRepository.save(program));
    }

    @PutMapping("/ecosystem/{id}")
    public ResponseEntity<?> updateProgram(
            @PathVariable Long id,
            @RequestBody EcosystemProgram updated) {
        return ecosystemProgramRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setDescription(updated.getDescription());
            p.setExternalUrl(updated.getExternalUrl());
            p.setLogoUrl(updated.getLogoUrl());
            return ResponseEntity.ok(ecosystemProgramRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/ecosystem/{id}")
    public ResponseEntity<?> deleteProgram(@PathVariable Long id) {
        if (!ecosystemProgramRepository.existsById(id))
            return ResponseEntity.notFound().build();
        ecosystemProgramRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Programme supprimé.", "id", id));
    }

    // ── LEADS PARTENAIRES ───────────────────────────

    @GetMapping("/partner-requests")
    public ResponseEntity<?> getPartnerRequests() {
        return ResponseEntity.ok(partnerRequestRepository.findAll());
    }

    @PutMapping("/partner-requests/{id}/process")
    public ResponseEntity<?> processPartnerRequest(@PathVariable Long id) {
        return partnerRequestRepository.findById(id).map(r -> {
            r.setProcessed(true);
            partnerRequestRepository.save(r);
            return ResponseEntity.ok(Map.of("id", id, "message", "Demande traitée."));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/partner-requests/{id}")
    public ResponseEntity<?> deletePartnerRequest(@PathVariable Long id) {
        if (!partnerRequestRepository.existsById(id))
            return ResponseEntity.notFound().build();
        partnerRequestRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Demande supprimée.", "id", id));
    }
}