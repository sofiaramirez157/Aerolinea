# Aerolinea

-----Aerolíneas DonBosco-----
Este proyecto implementa un sistema de gestión para una aerolínea, con funcionalidades como la gestión de usuarios, vuelos, reservas y destinos, asegurando la seguridad y eficiencia mediante tecnologías modernas como Spring Boot, JWT y Docker. Está diseñado para ser modular y escalable, permitiendo futuras expansiones o migración hacia microservicios.
Tabla de Contenidos
Descripción del Proyecto
Instalación y Configuración
Uso
Características
Tecnologías
Objetivos del Proyecto
Requisitos Funcionales
Requisitos No Funcionales
Descripción del Proyecto
El sistema permite gestionar:
•    Usuarios: Registro, autenticación y manejo de roles (ROLE_ADMIN, ROLE_USER).
•    Vuelos: Creación automática de vuelos, actualización de estado (disponible/no disponible).
•    Reservas: Creación de reservas solo cuando hay disponibilidad.
•    Destinos: CRUD para destinos, accesible solo para administradores.
El proyecto está basado en una arquitectura monolítica con Spring Boot y utiliza JWT para la autenticación segura. Además, Docker se usa para contenerización y MySQL para el manejo de la base de datos.
Instalación y Configuración
Sigue los pasos a continuación para configurar el proyecto localmente.
Clona el repositorio:
git clone https://github.com/sofiaramirez157/Aerolinea.git
Configura la base de datos en el archivo application.properties:
properties

spring.datasource.url=jdbc:mysql://localhost:3306/aerolinea_db
spring.datasource.username=root
spring.datasource.password=tu_password
Compila el proyecto e inicia el servidor:
mvn install
mvn spring-boot:run
Opcional: Dockerizar la aplicación
docker-compose up
GitHub
GitHub - sofiaramirez157/Aerolinea
Contribute to sofiaramirez157/Aerolinea development by creating an account on GitHub.
GitHub - sofiaramirez157/Aerolinea
Uso
Endpoints principales:
•    Autenticación:
o    /login: Inicia sesión y obtiene un token JWT.
•    Gestión de Usuarios:
o    /api/users: CRUD completo solo para administradores.
•    Gestión de Vuelos:
o    /api/vuelos: Crear, actualizar, eliminar y listar vuelos.
•    Gestión de Reservas:
o    /api/reservas: Reservar vuelos y verificar disponibilidad.
•    Gestión de Destinos:
o    /api/destinos: CRUD de destinos solo accesible para administradores.
Características
•    Gestión de usuarios: Roles diferenciados para administración y usuarios.
•    JWT para autenticación: Sesiones seguras mediante tokens.
•    Automatización: Cambios automáticos en el estado de vuelos basados en disponibilidad y fecha.
•    Pruebas automatizadas: Con Postman y GitHub Actions.
•    Docker: Contenerización de la aplicación para un despliegue rápido.
Tecnologías
•    Java 17
•    Spring Boot
•    Spring Security & JWT
•    MySQL para base de datos.
•    Maven para gestión de dependencias.
•    Docker para contenerización.
•    GitHub Actions para CI/CD.
Objetivos del Proyecto
Reforzar los conceptos de creación de APIs.
Aplicar relaciones en la base de datos.
Gestionar datos con concurrencia en Java.
Asentar conocimientos sobre autenticación con Spring Security y JWT.
Automatización de pruebas con GitHub Actions y Postman.
Requisitos Funcionales
•    Gestión de Usuarios: Registro, autenticación, y manejo de roles.
•    Gestión de Vuelos: Cambios automáticos en estado de vuelos.
•    Gestión de Reservas: Validación de disponibilidad antes de reservar.
•    Gestión de Destinos: CRUD de destinos, accesible solo para administradores.
•    Manejo de Excepciones: Gestión personalizada de errores.
Requisitos No Funcionales
•    Seguridad: Uso de JWT para proteger las APIs.
•    Rendimiento: Optimización del sistema para eficiencia.
•    Escalabilidad: Posibilidad de migración futura a microservicios.
•    Pruebas: Implementación de tests para asegurar estabilidad
