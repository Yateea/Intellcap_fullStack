package fr.intellcap.backend.controller;

import fr.intellcap.backend.repository.*;
import fr.intellcap.backend.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final FeaturedProjectRepository featuredProjectRepository;
    private final EcosystemProgramRepository ecosystemProgramRepository;
    private final PostRepository postRepository;
    private final ConsultingServiceRepository consultingServiceRepository;
    private final LocationRepository locationRepository;

    @GetMapping("/projects")
    public ResponseEntity<?> getFeaturedProjects() {
        return ResponseEntity.ok(featuredProjectRepository.findByActiveTrue());
    }

    @GetMapping("/ecosystem")
    public ResponseEntity<?> getEcosystem() {
        return ResponseEntity.ok(ecosystemProgramRepository.findByActiveTrue());
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(
            @RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return ResponseEntity.ok(
                postRepository.findByCategoryAndPublishedTrue(category));
        }
        return ResponseEntity.ok(
            postRepository.findByPublishedTrueOrderByCreatedAtDesc());
    }

    //  Incrémenter les vues — demande client point 1
    @PostMapping("/posts/{id}/view")
    public ResponseEntity<?> incrementView(@PathVariable Long id) {
        return postRepository.findById(id).map(post -> {
            post.setViewCount(post.getViewCount() + 1);
            postRepository.save(post);
            return ResponseEntity.ok(Map.of("views", post.getViewCount()));
        }).orElse(ResponseEntity.notFound().build());
    }

    //  Incrémenter les downloads — demande client point 1
    @PostMapping("/posts/{id}/download")
    public ResponseEntity<?> incrementDownload(@PathVariable Long id) {
        return postRepository.findById(id).map(post -> {
            post.setDownloadCount(post.getDownloadCount() + 1);
            postRepository.save(post);
            return ResponseEntity.ok(Map.of("downloads", post.getDownloadCount()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/consulting")
    public ResponseEntity<?> getConsultingServices(
            @RequestParam(required = false) String domain) {
        if (domain != null && !domain.isEmpty()) {
            return ResponseEntity.ok(
                consultingServiceRepository.findByDomainAndActiveTrue(domain));
        }
        return ResponseEntity.ok(consultingServiceRepository.findByActiveTrue());
    }

    @GetMapping("/locations")
    public ResponseEntity<?> getLocations(
            @RequestParam(required = false) String country) {
        if (country != null && !country.isEmpty()) {
            return ResponseEntity.ok(
                locationRepository.findByCountryCode(country.toUpperCase()));
        }
        return ResponseEntity.ok(locationRepository.findByActiveTrue());
    }

    @GetMapping("/overview")
    public ResponseEntity<?> getOverview() {
        var overview = new HashMap<String, Object>();
        overview.put("projects", featuredProjectRepository.findByActiveTrue());
        overview.put("ecosystem", ecosystemProgramRepository.findByActiveTrue());
        overview.put("locations", locationRepository.findByActiveTrue());
        overview.put("consulting", consultingServiceRepository.findByActiveTrue());
        overview.put("latestPosts",
            postRepository.findByPublishedTrueOrderByCreatedAtDesc()
                .stream().limit(3).toList());
        return ResponseEntity.ok(overview);
    }
}