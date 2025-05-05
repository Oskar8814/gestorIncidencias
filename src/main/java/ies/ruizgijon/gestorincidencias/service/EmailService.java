package ies.ruizgijon.gestorincidencias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // Constructor para la inyección de dependencias
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender; // Inicializa el JavaMailSender
    }

    public void enviarRecuperacion(String email, String enlace) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Recuperación de contraseña");

            String contenidoHtml = "<html>" +
                    "<body style=\"font-family: Arial, sans-serif;\">" +
                    "<h2 style=\"color: #2E86C1;\">Recuperación de contraseña</h2>" +
                    "<p>Haz clic en el siguiente enlace para restablecer tu contraseña:</p>" +
                    "<p><a href=\"" + enlace
                    + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #2E86C1; color: white; text-decoration: none; border-radius: 5px;\">Restablecer contraseña</a></p>"
                    +
                    "<p><strong>Nota:</strong> Este enlace es válido por <strong>1 hora</strong>. Después de ese tiempo, tendrás que solicitar uno nuevo.</p>"
                    +
                    "<p>Si tú no solicitaste este cambio, puedes ignorar este mensaje.</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(contenidoHtml, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
