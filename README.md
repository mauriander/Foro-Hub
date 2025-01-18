# Desafío: Crear un Foro API REST

## Introducción

En este reto, volcamos los conocimientos adquiridos en Java y Spring Boot para crear un sistema que permita a los usuarios interactuar en un foro, creando, actualizando, y eliminando tópicos, así como participando en las discusiones.

Este proyecto está basado en el concepto de un foro donde los usuarios pueden registrar sus dudas, sugerencias y respuestas. Además, se implementa una capa de seguridad utilizando JWT (JSON Web Tokens) para asegurar que solo los usuarios autenticados puedan realizar ciertas acciones como crear o eliminar tópicos.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación para el backend.
- **Spring Boot**: Framework para el desarrollo rápido de aplicaciones Java.
- **Spring Security**: Para implementar la autenticación con JWT.
- **JPA/Hibernate**: Para la persistencia de datos.
- **Insomnia/Postman**: Herramientas para probar los endpoints de la API.
- **Base de Datos**: Puedes usar la base de datos de tu preferencia (H2, MySQL, PostgreSQL, etc.).

## Endpoints

Aquí están los principales endpoints de nuestra API REST para gestionar tópicos y respuestas en el foro. Los métodos `POST` están marcados en verde, los `DELETE` en rojo y los `GET` en azul para facilitar su identificación.

#### **Registrar un nuevo Tópico (POST)**

- **Ruta**: `/topicos/registrar`
- **Descripción**: Permite a un usuario registrar un nuevo tópico en el foro.
- **Autenticación**: Requiere un token JWT.
- **Request Body**:

```json
{
  "idUsuario": 1,
  "mensaje": "¿Cómo implementar seguridad en Spring?",
  "nombreCurso": "Spring Boot",
  "titulo": "Seguridad en Spring"
}

## Endpoints

### 1. **Registrar un tópico**

- **Método:**
  URL: /topicos/registrar

Descripción: Registra un nuevo tópico en el foro.

Cuerpo de la solicitud (Request Body):

{
"titulo": "Título del tópico",
"mensaje": "Contenido del tópico",
"autor": "Nombre del autor",
"nombreCurso": "Nombre del curso"
}

Respuesta (Response):

    Código de estado: 200 OK
    Cuerpo de la respuesta:

    {
      "id": 1,
      "titulo": "Título del tópico",
      "mensaje": "Contenido del tópico",
      "autor": "Nombre del autor",
      "nombreCurso": "Nombre del curso",
      "fecha": "2025-01-18T00:00:00"
    }

2. Listar tópicos

   Método:

<span style="color:black">GET</span>

URL: /topicos/listar

Descripción: Obtiene una lista de los tópicos activos.

Respuesta (Response):

    Código de estado: 200 OK
    Cuerpo de la respuesta:

    [
      {
        "id": 1,
        "titulo": "Título del tópico",
        "mensaje": "Contenido del tópico",
        "fecha": "2025-01-18T00:00:00"
      }
    ]

3. Actualizar un tópico

   Método:

<span style="color:blue">PUT</span>

URL: /topicos/actualizar/{id}

Descripción: Actualiza un tópico existente.

Cuerpo de la solicitud (Request Body):

{
"titulo": "Nuevo título",
"mensaje": "Nuevo contenido"
}

Respuesta (Response):

    Código de estado: 200 OK
    Cuerpo de la respuesta:

    {
      "id": 1,
      "titulo": "Nuevo título",
      "mensaje": "Nuevo contenido",
      "fecha": "2025-01-18T00:00:00"
    }

4. Eliminar un tópico

   Método:

   <span style="color:red">DELETE</span>

   URL: /topicos/eliminar/{id}

   Descripción: Elimina un tópico existente (lo desactiva).

   Respuesta (Response):
   Código de estado: 204 No Content

5. Registrar un usuario

   Método:

<span style="color:green">POST</span>

URL: /usuario/registro

Descripción: Registra un nuevo usuario en el sistema.

Cuerpo de la solicitud (Request Body):

{
"nombre": "Juan Pérez",
"email": "juan.perez@example.com",
"username": "juan.perez",
"password": "clave1234"
}

Respuesta (Response):

    Código de estado: 201 Created
    Cuerpo de la respuesta:

    {
      "id": 1,
      "nombre": "Juan Pérez"
    }

6. Login de usuario

   Método:

<span style="color:green">POST</span>

URL: /usuario/login

Descripción: Inicia sesión con las credenciales del usuario.

Cuerpo de la solicitud (Request Body):

{
"email": "juan.perez@example.com",
"password": "clave1234"
}

Respuesta (Response):

    Código de estado: 200 OK
    Cuerpo de la respuesta:

{
"token": "JWTTokenAquí"
}
```
