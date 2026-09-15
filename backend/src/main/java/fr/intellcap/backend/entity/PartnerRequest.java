package fr.intellcap.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partner_requests")
public class PartnerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom complet est obligatoire")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "L'organisme est obligatoire")
    @Column(nullable = false)
    private String organization;

    @NotBlank(message = "Le pays est obligatoire")
    @Column(nullable = false)
    private String country;

    @NotBlank(message = "La ville est obligatoire")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "L'email professionnel est obligatoire")
    @Email(message = "Format email invalide")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Le WhatsApp est obligatoire")
    @Pattern(
        regexp = "^\\+[1-9]\\d{6,14}$",
        message = "Format international requis (ex: +33612345678)"
    )
    @Column(nullable = false)
    private String whatsapp;

    @NotBlank(message = "L'expression de besoin est obligatoire")
    @Size(max = 1000, message = "L'expression de besoin ne peut dépasser 1000 caractères")
    @Column(nullable = false, length = 1000)
    private String needExpression;

    // Champs optionnels
    private String linkedin;
    private Integer surfaceAvailable;
    private String preciseLocation;
    private Integer humanResources;
    private String estimatedBudget;

    private boolean processed = false;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public PartnerRequest() {}

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getOrganization() { return organization; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getEmail() { return email; }
    public String getWhatsapp() { return whatsapp; }
    public String getNeedExpression() { return needExpression; }
    public String getLinkedin() { return linkedin; }
    public Integer getSurfaceAvailable() { return surfaceAvailable; }
    public String getPreciseLocation() { return preciseLocation; }
    public Integer getHumanResources() { return humanResources; }
    public String getEstimatedBudget() { return estimatedBudget; }
    public boolean isProcessed() { return processed; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setOrganization(String organization) { this.organization = organization; }
    public void setCountry(String country) { this.country = country; }
    public void setCity(String city) { this.city = city; }
    public void setEmail(String email) { this.email = email; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }
    public void setNeedExpression(String needExpression) { this.needExpression = needExpression; }
    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }
    public void setSurfaceAvailable(Integer surfaceAvailable) { this.surfaceAvailable = surfaceAvailable; }
    public void setPreciseLocation(String preciseLocation) { this.preciseLocation = preciseLocation; }
    public void setHumanResources(Integer humanResources) { this.humanResources = humanResources; }
    public void setEstimatedBudget(String estimatedBudget) { this.estimatedBudget = estimatedBudget; }
    public void setProcessed(boolean processed) { this.processed = processed; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}