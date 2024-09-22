# Ponti-Movil

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.2-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.8.4-orange)

`Ponti-Movil` es un proyecto de demostración para Spring Boot, diseñado para ilustrar la configuración y uso de diversas dependencias en un proyecto Maven.

## Características

- **Lenguaje:** Java
- **Spring Boot:** 3.3.2
- **Empaque:** Jar
- **Versión de Java:** 17

## Colaboradores

Conoce al equipo detrás de **Ponti-Movil**:

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/DavzC" target="_blank">
        <img src="https://github.com/Nidhood/Ponti-movil/blob/main/photos/david-ortizpp.jpeg?s=100" alt="David" width="100" height="100">
        <p><strong>David Santiago Ortiz Calderon</strong><br>Backend & Frontend Developer</p>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/judagogu8" target="_blank">
        <img src="https://github.com/Nidhood/Ponti-movil/blob/main/photos/JuanGonzalez.JPG?s=100" alt="Juan Gonzalez" width="100" height="120">
        <p><strong>Juan David González</strong><br>CEO Ponti-Movil</p>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Nidhood" target="_blank">
        <img src="https://github.com/Nidhood/Ponti-movil/blob/main/photos/Ivan-Orozco.jpeg?s=100" alt="Ivan Dario Orozco" width="100" height="100">
        <p><strong>Ivan Dario Orozco</strong><br>Backend & Frontend Developer</p>
      </a>
    </td>
  </tr>
</table>

## Dependencias

### Dependencias Principales

- **[Spring Web](https://spring.io/projects/spring-web)**: Proporciona las funcionalidades básicas para construir aplicaciones web con Spring MVC.

- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)**: Simplifica el acceso a datos y la gestión de la base de datos con JPA.

- **[Spring HATEOAS](https://spring.io/projects/spring-hateoas)**: Facilita la implementación de enlaces en recursos RESTful.

- **[Liquibase](https://www.liquibase.org/)**: Herramienta de migración para gestionar cambios en la base de datos.

- **[PostgreSQL Driver](https://jdbc.postgresql.org/)**: Controlador JDBC para conectar con bases de datos PostgreSQL.

### Otras Dependencias

- **[Lombok](https://projectlombok.org/)**: Reduce el código boilerplate con anotaciones para generar automáticamente getters, setters, constructores, etc.

- **[Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.devtools)**: Proporciona herramientas para mejorar el desarrollo con recarga automática y otras características.

- **[Spring Boot Starter Test](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-testing)**: Incluye las herramientas necesarias para pruebas unitarias y de integración.

- **[Spring Boot Starter Thymeleaf](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-use-thymeleaf)**: Soporte para el motor de plantillas Thymeleaf.

## Construcción y Ejecución

Para construir y ejecutar el proyecto, sigue estos pasos:

1. **Clona el repositorio:**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```

2. **Navega al directorio del proyecto:**
   ```bash
   cd ponti-movil
   ```

3. **Construye el proyecto con Maven:**
   ```bash
   mvn clean install
   ```

4. **Ejecuta la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

## Configuración

Configura el archivo `application.properties` en el directorio `src/main/resources` para conectar con tu base de datos PostgreSQL y ajustar otros parámetros específicos de tu entorno.

## Contribuciones

Las contribuciones son bienvenidas. Abre un *issue* o envía un *pull request* para contribuir al proyecto.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

