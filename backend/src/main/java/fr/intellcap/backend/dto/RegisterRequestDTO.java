package fr.intellcap.backend.dto;

import jakarta.validation.constraints.*;

public class RegisterRequestDTO {
    @NotBlank @Size(min = 2)
    private String fullName;
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 6)
    private String password;

    public RegisterRequestDTO() {}

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}