package fr.intellcap.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "featured_projects")
public class FeaturedProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String domain;

    @Column(nullable = false, length = 2000)
    private String description;

    private String logoUrl;
    private boolean active = true;

    public FeaturedProject() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDomain() { return domain; }
    public String getDescription() { return description; }
    public String getLogoUrl() { return logoUrl; }
    public boolean isActive() { return active; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDomain(String domain) { this.domain = domain; }
    public void setDescription(String description) { this.description = description; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public void setActive(boolean active) { this.active = active; }
}