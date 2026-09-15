package fr.intellcap.backend.service;

import fr.intellcap.backend.entity.PartnerRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${partner.email.to:contact@intellcap.fr}")
    private String partnerEmailTo;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPartnerRequestNotification(PartnerRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(partnerEmailTo);
        message.setSubject("[INTELLCAP] Nouvelle demande de " + request.getFullName());

        StringBuilder body = new StringBuilder();
        body.append("Nouvelle demande de partenariat :\n\n");
        body.append("Nom : ").append(request.getFullName()).append("\n");
        body.append("Organisme : ").append(request.getOrganization()).append("\n");
        body.append("Pays : ").append(request.getCountry()).append("\n");
        body.append("Ville : ").append(request.getCity()).append("\n");
        body.append("Email : ").append(request.getEmail()).append("\n");
        body.append("WhatsApp : ").append(request.getWhatsapp()).append("\n");
        if (request.getLinkedin() != null) {
            body.append("LinkedIn : ").append(request.getLinkedin()).append("\n");
        }
        body.append("\nBesoin :\n").append(request.getNeedExpression()).append("\n");

        if (request.getSurfaceAvailable() != null) {
            body.append("\nSurface : ").append(request.getSurfaceAvailable()).append(" m²\n");
        }
        if (request.getHumanResources() != null) {
            body.append("RH : ").append(request.getHumanResources()).append("\n");
        }
        if (request.getEstimatedBudget() != null) {
            body.append("Budget : ").append(request.getEstimatedBudget()).append("\n");
        }

        message.setText(body.toString());
        mailSender.send(message);
    }
}