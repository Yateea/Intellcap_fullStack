package fr.intellcap.backend.dto;

import jakarta.validation.constraints.*;

public class PartnerRequestDTO {

    @NotBlank(message = "Le nom complet est obligatoire")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
    private String fullName;

    @NotBlank(message = "L'organisme est obligatoire")
    private String organization;

    @NotBlank(message = "Le pays est obligatoire")
    private String country;

    @NotBlank(message = "La ville est obligatoire")
    private String city;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le WhatsApp est obligatoire")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Format international requis: +XXX...")
    private String whatsapp;

    @NotBlank(message = "L'expression de besoin est obligatoire")
    @Size(max = 1000, message = "Maximum 1000 caractères")
    private String needExpression;

    private String linkedin;
    private Integer surfaceAvailable;
    private String preciseLocation;
    private Integer humanResources;
    private String estimatedBudget;

    @NotBlank(message = "Token reCAPTCHA obligatoire")
    private String recaptchaToken;

    public PartnerRequestDTO() {}

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
    public String getRecaptchaToken() { return recaptchaToken; }

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
    public void setRecaptchaToken(String recaptchaToken) { this.recaptchaToken = recaptchaToken; }
}