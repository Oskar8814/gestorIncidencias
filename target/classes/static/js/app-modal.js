//Modal imÃ¡genes
const modalImagen = document.querySelector('#modal-imagen');
    
modalImagen.addEventListener('show.bs.modal', function(event) {
    const enlace = event.relatedTarget; // Elemento que disparó el modal

    const rutaImagen = enlace.getAttribute('data-bs-imagen'); // Obtener la ruta de la imagen
    const tituloIncidencia = enlace.closest('tr').querySelector('td:nth-child(2)').textContent; // Obtener el título de la incidencia

    // Crear la imagen
    const imagen = document.createElement('IMG');
    imagen.src = `img/${rutaImagen}.jpg`;
    imagen.classList.add('img-fluid');
    imagen.alt = 'Imagen incidencia';

    // Obtener elementos del modal
    const contenidoModal = document.querySelector('.modal-body');
    const tituloModal = document.querySelector('#tittleModal');

    // Limpiar el contenido anterior
    contenidoModal.textContent = '';

    // Asignar el título y la imagen al modal
    tituloModal.textContent = tituloIncidencia;
    contenidoModal.appendChild(imagen);
});


// Para cerrar el modal y limpiar contenido
modalImagen.addEventListener('hidden.bs.modal', function() {
    document.querySelector('.modal-body').textContent = '';
    document.querySelector('#tittleModal').textContent = '';
});