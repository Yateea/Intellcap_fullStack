package fr.intellcap.backend.repository;

import fr.intellcap.backend.entity.EcosystemProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EcosystemProgramRepository
        extends JpaRepository<EcosystemProgram, Long> {
    List<EcosystemProgram> findByActiveTrue();
}