package fr.intellcap.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ecosystem_programs")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EcosystemProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Elev8, InnovDays, Constellium, Startups

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String externalUrl; // URL vers la plateforme dédiée

    private String logoUrl;

    @Builder.Default
    private boolean active = true;
}