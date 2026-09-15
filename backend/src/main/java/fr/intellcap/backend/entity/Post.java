package fr.intellcap.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false)
    private String category; // AEROSPACE, AI, QUANTUM, ROBOTICS, HEALTH, CYBERSECURITY

    @Column(nullable = false)
    private String author;

    private String imageUrl;

    
    @Builder.Default
    private Long viewCount = 0L;      // nombre de consultations

    @Builder.Default
    private Long downloadCount = 0L;  // nombre de downloads

    @Builder.Default
    private boolean published = false;

    @Builder.Default
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}