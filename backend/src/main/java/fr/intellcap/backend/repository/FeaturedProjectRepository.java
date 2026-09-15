package fr.intellcap.backend.repository;

import fr.intellcap.backend.entity.FeaturedProject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeaturedProjectRepository
        extends JpaRepository<FeaturedProject, Long> {
    List<FeaturedProject> findByActiveTrue();
}