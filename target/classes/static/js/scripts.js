// Guardamos en una variable una funcion que muestra un cuadro de dialogo de tipo confirm para eliminar una incidencia
let dialogoConfirm = function (e) {
    if (!confirm("¿Realmente desea eliminar la incidencia?")) e.preventDefault();
};

// Guardamos en una variable una funcion que muestra un cuadro de dialogo de tipo confirm para eliminar un usuario
let dialogoConfirmDeleteUsuario = function (e) {
    if (!confirm("¿Realmente desea eliminar el Usuario?")) e.preventDefault();
};

// Aplicamos a todos los elementos HTML que tengan la clase CSS 'confirmar' el evento click para que muestre el cuadro de dialogo de confirmacion.
document.querySelectorAll(".confirmar").forEach(function (elemento) {
    elemento.addEventListener("click", dialogoConfirm, false);
});

// Aplicamos a todos los elementos HTML que tengan la clase CSS 'confirmarDeleteUsuario' el evento click para que muestre el cuadro de dialogo de confirmacion.
document.querySelectorAll(".confirmarDeleteUsuario").forEach(function (elemento) {
    elemento.addEventListener("click", dialogoConfirmDeleteUsuario, false);
});

document.addEventListener("DOMContentLoaded", function () {
    const togglePassword = document.getElementById("togglePassword");
    const passwordInput = document.getElementById("password");

    togglePassword.addEventListener("click", function () {
        const isPassword = passwordInput.type === "password";
        passwordInput.type = isPassword ? "text" : "password";
        this.classList.toggle("bi-eye");
        this.classList.toggle("bi-eye-slash");
        this.classList.toggle("text-primary");
    });
});

// Inicializamos el DataTable para la tabla de incidencias
$(document).ready(function () {
    $("#tabla-incidencias").DataTable({
        language: {
            url: "https://cdn.datatables.net/plug-ins/1.13.7/i18n/es-ES.json",
            info: "Incidencias _START_ a _END_ de un total de _TOTAL_",
            lengthMenu: "Mostrar _MENU_ incidencias por página"
        },
        searching: false,
        paging: false // Desactiva la paginación
    });
});
