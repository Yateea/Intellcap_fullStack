package fr.intellcap.backend.repository;

import fr.intellcap.backend.entity.PartnerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRequestRepository extends JpaRepository<PartnerRequest, Long> {
    boolean existsByEmail(String email);
}