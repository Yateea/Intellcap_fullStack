package fr.intellcap.backend.repository;

import fr.intellcap.backend.entity.ConsultingService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsultingServiceRepository
        extends JpaRepository<ConsultingService, Long> {
    List<ConsultingService> findByActiveTrue();
    List<ConsultingService> findByDomainAndActiveTrue(String domain);
}