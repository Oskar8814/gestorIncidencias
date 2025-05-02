package ies.ruizgijon.gestorincidencias.util;

import java.util.regex.Pattern;

import ies.ruizgijon.gestorincidencias.model.Usuario;

public class Validaciones {

	private Validaciones() {
		// Constructor privado para evitar la instanciación
	}

	/**
	 * Patrón de expresión regular para validar la contraseña del usuario.
	 * La contraseña debe tener entre 12 y 16 caracteres, al menos una letra
	 * mayúscula, una letra minúscula, un número y un carácter especial.
	 */
	private static final Pattern PATRON_CONTRASENA = Pattern.compile(
			"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,16}$");

	/**
	 * Valida si un objeto Usuario cumple con los requisitos de la base de datos y
	 * los criterios de seguridad.
	 * 
	 * @param usuario Usuario a validar
	 * @return true si es válido, false si no lo es
	 */
	public static boolean validarUsuario(Usuario usuario) {
		boolean esValido = false;
		if (usuario != null) {
			esValido = esTextoValido(usuario.getNombre(), 50)
					&& esTextoValido(usuario.getApellido1(), 50)
					&& esTextoValido(usuario.getMail(), 100)
					&& esPasswordValida(usuario.getPassword()); // Descomentado para evitar que falle la validación de contraseña
		}
		return esValido;

	}

	private static boolean esTextoValido(String valor, int maxLength) {
		return valor != null && !valor.trim().isEmpty() && valor.length() <= maxLength;
	}

	public static boolean esPasswordValida(String pass) {
		return pass != null && PATRON_CONTRASENA.matcher(pass).matches();
	}

}
