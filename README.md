# Gestor Incidencias

Proyecto final del ciclo de Desarrollo de Aplicaciones Web.  
**Gestor Incidencias** es una aplicación web dirigida a la gestión eficiente de incidencias en un centro escolar.

---

## Descripción

Gestor Incidencias centraliza el registro, seguimiento y resolución de incidencias dentro de una comunidad educativa. Facilita la comunicación entre los distintos perfiles del centro (usuario, técnico y administración), simplificando el ciclo de vida de cada incidencia desde su creación hasta su resolución.

### Principales funcionalidades

- **Creación y gestión de incidencias:** Cualquier usuario autenticado puede reportar incidencias, incluyendo descripciones, imágenes y notas asociadas.
- **Ciclo de vida de incidencias:** Las incidencias pasan por diferentes estados: Pendiente, En Progreso y Resuelta. Los gestores pueden asignarse incidencias y cambiar su estado según el progreso.
- **Notas y comunicación:** Los usuarios pueden añadir notas a cada incidencia, permitiendo el seguimiento detallado y colaborativo de la resolución.
- **Filtrado y búsqueda avanzada:** Visualización de incidencias filtradas por estado, responsable, palabras clave, etc.
- **Historial y trazabilidad:** Todas las acciones y cambios de estado quedan reflejados para un historial completo.
- **Control de permisos:** Diferenciación de roles (usuario, técnico, administrador) para limitar o permitir ciertas acciones.

---

## Ejemplo de flujo de uso

1. **Creación:**  
   Un usuario (usuario, técnico o administrativo) reporta una nueva incidencia, añade una descripción y puede adjuntar una imagen. La incidencia se registra en estado "Pendiente".

2. **Asignación:**  
   Un gestor (por ejemplo, técnico) revisa la lista de incidencias pendientes y se asigna aquellas que va a resolver. El estado pasa a "En Progreso".

3. **Seguimiento:**  
   Los participantes pueden añadir notas o comentarios para actualizar el avance, solicitar información adicional, etc.

4. **Resolución:**  
   Cuando la incidencia ha sido solucionada, el gestor marca la incidencia como "Resuelta". Esta pasa al historial, donde puede ser consultada en cualquier momento.

5. **Consulta:**  
   Todos los usuarios pueden revisar incidencias actuales o pasadas, aplicar filtros y realizar búsquedas.

---

## Screenshots

> Añade imágenes aquí cuando las tengas. Por ejemplo:

![Pantalla principal de incidencias](screenshots/incidencias-listado.png)
![Formulario de nueva incidencia](screenshots/nueva-incidencia.png)

---

## Preguntas Frecuentes (FAQ)

### ¿Quién puede crear incidencias?
Cualquier usuario autenticado (usuario, técnico, personal administrativo) puede registrar incidencias.

### ¿Cómo se asigna una incidencia a un gestor?
El personal encargado puede ver las incidencias pendientes y asignarse aquellas que desee gestionar.

### ¿Se pueden añadir comentarios o notas a una incidencia?
Sí, todos los participantes (creador, gestor, otros autorizados) pueden añadir notas para realizar un seguimiento y dejar constancia de las acciones realizadas.

### ¿Puedo consultar las incidencias resueltas?
Sí, el sistema mantiene un historial y permite consultar y filtrar incidencias por estado, fecha, responsable, etc.

### ¿Qué ocurre si una incidencia no se resuelve?
La incidencia permanecerá en estado "Pendiente" o "En Progreso" hasta que un gestor la marque como resuelta.

---

## Tecnologías utilizadas

- **Backend:** Java, Spring Boot
- **Frontend:** JS, Thymeleaf, Bootstrap 5 + Sass
- **Base de datos:** MySQL
- **Contenedores:** Docker (incluye un `Dockerfile` para despliegue)
- **Gestión de dependencias:** Maven

---

## Instalación

### Requisitos previos

- Java 17 o superior
- Maven 3.x
- Docker (opcional, para despliegue contenerizado)
- *(Base de datos, si es necesario instalar por separado)*

### Pasos para la instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/Oskar8814/gestorIncidencias.git
   cd gestorIncidencias
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean install
   ```

3. **Ejecutar localmente**
   ```bash
   mvn spring-boot:run
   ```

4. **O ejecutar con Docker**
   ```bash
   docker build -t gestorincidencias .
   docker run -p 8080:8080 gestorincidencias
   ```

---

## Uso

Accede a la aplicación en [http://localhost:8080](http://localhost:8080) o en https://gestionincidenciasruix.onrender.com

---

## Diagramas

Encuentra un diagrama de la arquitectura en el archivo [`diagrama.puml`](diagrama.puml).

---

## Ayuda

Consulta el archivo [`HELP.md`](HELP.md) para preguntas frecuentes, ejemplos de uso y solución de problemas.

---

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo [`LICENSE`](LICENSE) para más información.

---

## Autor

Desarrollado por Oskar8814

---
