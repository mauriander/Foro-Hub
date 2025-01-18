# Documentación de la API - Foro Hub

Bienvenido a la documentación de la API del proyecto Foro Hub. A continuación, se detallan los endpoints de la API, junto con los métodos HTTP, sus descripciones y la salida esperada.

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
