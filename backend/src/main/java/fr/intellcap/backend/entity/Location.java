package fr.intellcap.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country; // France, Luxembourg, Japan, Morocco

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    private Double latitude;
    private Double longitude;

    @Column(nullable = false)
    private String countryCode; // FRA, LUX, JPN, MOR

    private String phone;
    private String email;

    @Builder.Default
    private boolean active = true;
}