package fr.intellcap.backend.dto;

import jakarta.validation.constraints.*;

public class LoginRequestDTO {
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 6)
    private String password;

    public LoginRequestDTO() {}

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}