package com.example.Foro_Hub.controller;
import com.example.Foro_Hub.domain.usuario.UsuarioService;
import com.example.Foro_Hub.domain.usuario.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /***********************************
     * REST API POST
     * Nuevo Registro
     * ENDPOINT :
     * http://localhost:8080/usuario
     ************************************/
    @PostMapping("/registro")
    @Transactional
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid RegistroUsuarioDTO registroUsuarioDTO, UriComponentsBuilder uriComponentsBuilder) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.registrarUsuario(registroUsuarioDTO);
            // Asegúrate de que usuarioDTO tenga los atributos correctos, como id y nombre.
            RespuestaUsuarioDTO respuesta = new RespuestaUsuarioDTO(usuarioDTO.id(), usuarioDTO.nombre());
            URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuarioDTO.id()).toUri();
            return ResponseEntity.created(url).body(respuesta);
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.badRequest().body("Validation failed: " + ex.getMessage());
        }
    }
    /**************************************
     * REST API GET
     * Obtener todos los Usuarios
     * ENDPOINT :
     * http://localhost:8080/usuario/usuarios
     ***************************************/
    @GetMapping("/usuarios")
    public ResponseEntity<Page<ListarUsuariosDTO>> listarUsuarios(@PageableDefault(size = 10) Pageable paged) {
        return ResponseEntity.ok(usuarioRepository.findByActivoTrue(paged).map(ListarUsuariosDTO::new));
    }

    /************************************************
     * REST API PUT
     * Actualizar un usuario por id
     * ENDPOINT :
     * http://localhost:8080/usuario/1
     *************************************************/
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ActualizacionUsuarioDTO> actualizacionUsuario(@PathVariable Long id,
                                                                        @RequestBody @Valid ActualizacionUsuarioDTO actualizacionUsuarioDTO) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.actualizacionUsuario(actualizacionUsuarioDTO);
        //return ResponseEntity.ok(new ActualizacionUsuarioDTO(usuario.getId(), usuario.getName(), usuario.getEmail(),usuario.getPassword(),usuario.toString(), usuario.getUsername()));
        return ResponseEntity.ok(
                new ActualizacionUsuarioDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.isActivo(),
                        usuario.getPassword(),
                        usuario.getUsername()
                )
        );


    }

    /************************************************
     * REST API DELETE
     * Eliminar un usuario por id
     * ENDPOINT :
     * http://localhost:8080/usuario/1
     *************************************************/
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.deactivateUser(); // Desactivar el usuario (sin eliminar físicamente)
        return ResponseEntity.noContent().build();
    }

    /*******************************************
     * REST API GET
     * Obtener un Usuario pasando el id
     * ENDPOINT :
     * http://localhost:8080/usuario/1
     ********************************************/
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var usuarioDetail = new RespuestaUsuarioDTO(usuario.getId(), usuario.getNombre());
        return ResponseEntity.ok(usuarioDetail);
    }

    @RequestMapping("/usuarios")
    public class InactivacionUsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @PostMapping("/desactivar/{id}")
        public RegistroUsuarioDTO desactivarUsuario(@PathVariable Long id) {
            return usuarioService.desactivarUsuario(id);
        }
    }
}
