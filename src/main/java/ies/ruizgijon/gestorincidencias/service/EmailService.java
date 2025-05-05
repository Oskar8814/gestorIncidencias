package ies.ruizgijon.gestorincidencias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarRecuperacion(String email, String enlace) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Recuperación de contraseña");
        msg.setText("Haz clic para restablecer tu contraseña: " + enlace);
        mailSender.send(msg);
    }
}

