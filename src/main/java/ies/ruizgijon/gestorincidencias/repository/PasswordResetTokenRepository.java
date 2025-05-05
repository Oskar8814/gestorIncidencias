package ies.ruizgijon.gestorincidencias.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.ruizgijon.gestorincidencias.model.PasswordResetToken;
import ies.ruizgijon.gestorincidencias.model.Usuario;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUsuario(Usuario usuario);
}
