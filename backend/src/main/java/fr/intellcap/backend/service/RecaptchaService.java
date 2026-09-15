package fr.intellcap.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret:}")
    private String recaptchaSecret;

    private static final String RECAPTCHA_VERIFY_URL =
        "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyToken(String token) {
        // ✅ Si pas de secret configuré (dev), on laisse passer
        if (recaptchaSecret == null || recaptchaSecret.isBlank()) {
            return true;
        }

        if (token == null || token.isBlank()) {
            return false;
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = RECAPTCHA_VERIFY_URL
                + "?secret=" + recaptchaSecret
                + "&response=" + token;

            // ✅ ParameterizedTypeReference élimine le unchecked cast
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();
            return body != null && Boolean.TRUE.equals(body.get("success"));

        } catch (Exception e) {
            return false;
        }
    }
}