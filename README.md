# Proyecto RA9 - Taller mecánico 

Este proyecto es una página web hecha con Spring Boot para gestionar un taller.

Tiene dos entidades principales (models): Cliente y Reparación.

## Cliente
Atributos: id, nombre, teléfono, email, matrícula

## Reparación
Atributos: id, descripción, fechaEntrada, fechaSalida, costeEuros, estado

## Relaciones JPA
Un cliente puede tener varias reparaciones @OneToMany, pero cada reparación solo pertenece a un cliente @ManyToOne

Al borrar un cliente se eliminan también sus reparaciones

## ¿Con qué lo he hecho?
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Thymeleaf
- MySQL
- Docker

## ¿Cómo ejecutar el proyecto?
docker compose up --build

mvn spring-boot:run

Navegador: https://localhost:8080

Puerto: 13307:3306

## ¿Qué funciones tiene?
Es un CRUD: 

- Cliente: 
    - crear cliente
    - editar cliente
    - borrar cliente
    - buscar cliente
    - listar las reparaciones de cada cliente

- Reparación:
    - crear reparación
    - editar reparación
    - borrar reparación
    - filtrar reparaciones según su estado
    - buscar reparaciones entre dos fechas


