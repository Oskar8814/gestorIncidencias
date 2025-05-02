package ies.ruizgijon.gestorincidencias.util;

import java.util.ArrayList;
import java.util.List;
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
	 * @param usuario El objeto Usuario a validar.
	 * @return Una lista de errores de validación. Si la lista está vacía, el usuario es valido
	 */
	public static List<String> obtenerErroresValidacionUsuario(Usuario usuario) {
		List<String> errores = new ArrayList<>();

		if (usuario == null) {
			errores.add("El objeto Usuario es nulo.");
			return errores;
		}

		if (!esTextoValido(usuario.getNombre(), 50)) {
			errores.add("El nombre no es válido o excede los 50 caracteres.");
		}

		if (!esTextoValido(usuario.getApellido1(), 50)) {
			errores.add("El primer apellido no es válido o excede los 50 caracteres.");
		}

		if (!esTextoValido(usuario.getMail(), 100)) {
			errores.add("El correo electrónico no es válido o excede los 100 caracteres.");
		}

		if (!esPasswordValida(usuario.getPassword())) {
			errores.add("La contraseña no cumple con los requisitos de seguridad. Debe tener entre 12 y 16 caracteres, al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.");
		}

		return errores;
	}

	private static boolean esTextoValido(String valor, int maxLength) {
		return valor != null && !valor.trim().isEmpty() && valor.length() <= maxLength;
	}

	public static boolean esPasswordValida(String pass) {
		return pass != null && PATRON_CONTRASENA.matcher(pass).matches();
	}

}
