package com.example.Foro_Hub.domain.usuario;

import com.example.Foro_Hub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public RegistroUsuarioDTO registrarUsuario(RegistroUsuarioDTO registroUsuarioDTO) {
        if (usuarioRepository.findByEmail(registroUsuarioDTO.email()).isPresent()) {
            throw new ValidacionDeIntegridad("Este correo electrónico ya está registrado.");
        }
        if (usuarioRepository.findByUsername(registroUsuarioDTO.username()).isPresent()) {
            throw new ValidacionDeIntegridad("Este nombre de usuario ya está en uso.");
        }

        var usuario = new Usuario(
                registroUsuarioDTO,
                passwordEncoder
        );

        usuarioRepository.save(usuario);

        return new RegistroUsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getPassword()
        );
    }

    public RegistroUsuarioDTO actualizacionUsuario(Long id, ActualizacionUsuarioDTO actualizacionUsuarioDTO) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionDeIntegridad("El usuario con el ID proporcionado no existe."));

        // Actualizar los campos si se proporcionan nuevos valores
        if (actualizacionUsuarioDTO.nombre() != null) {
            usuario.setNombre(actualizacionUsuarioDTO.nombre());
        }
        if (actualizacionUsuarioDTO.email() != null) {
            usuario.setEmail(actualizacionUsuarioDTO.email());
        }
        if (actualizacionUsuarioDTO.username() != null) {
            usuario.setUsername(actualizacionUsuarioDTO.username());
        }
        if (actualizacionUsuarioDTO.clave() != null && !actualizacionUsuarioDTO.clave().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(actualizacionUsuarioDTO.clave()));
        }

        usuarioRepository.save(usuario);

        return new RegistroUsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getPassword()
        );
    }

    public RegistroUsuarioDTO desactivarUsuario(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionDeIntegridad("El usuario con el ID proporcionado no existe."));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);

        return new RegistroUsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getPassword()
        );
    }
}
