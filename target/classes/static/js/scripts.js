// Guardamos en una variable una funcion que muestra un cuadro de dialogo de tipo confirm para eliminar una incidencia
var dialogoConfirm = function (e) {
    if (!confirm("¿Realmente desea eliminar la incidencia?")) e.preventDefault();
};

// Guardamos en una variable una funcion que muestra un cuadro de dialogo de tipo confirm para eliminar un usuario
var dialogoConfirmDeleteUsuario = function (e) {
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