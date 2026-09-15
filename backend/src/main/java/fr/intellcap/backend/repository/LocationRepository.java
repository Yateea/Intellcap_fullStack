package fr.intellcap.backend.repository;

import fr.intellcap.backend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByActiveTrue();
    List<Location> findByCountryCode(String countryCode);
}