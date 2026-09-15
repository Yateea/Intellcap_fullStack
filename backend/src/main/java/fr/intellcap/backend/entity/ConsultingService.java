package fr.intellcap.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "consulting_services")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ConsultingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String domain; // AEROSPACE, AI, QUANTUM, ROBOTICS

    private String iconUrl;

    @Builder.Default
    private boolean active = true;
}