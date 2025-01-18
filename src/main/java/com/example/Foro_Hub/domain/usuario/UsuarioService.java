package com.example.Foro_Hub.domain.usuario;
import com.example.Foro_Hub.domain.usuario.UsuarioService;
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

    public UsuarioDTO registrarUsuario(RegistroUsuarioDTO registroUsuarioDTO) {
        validarUnicidadUsuario(registroUsuarioDTO);

        Usuario usuario = new Usuario(registroUsuarioDTO, passwordEncoder);
        usuarioRepository.save(usuario);

        // Ahora asegurate de devolver todos los campos necesarios
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getPassword() // O no devolver la contraseña por seguridad si es necesario
        );
    }

    private void validarUnicidadUsuario(RegistroUsuarioDTO registroUsuarioDTO) {
        if (usuarioRepository.existsByEmail(registroUsuarioDTO.email())) {
            throw new ValidacionDeIntegridad("Este correo electrónico ya está registrado.");
        }
        if (usuarioRepository.existsByUsername(registroUsuarioDTO.username())) {
            throw new ValidacionDeIntegridad("Este nombre de usuario ya está en uso.");
        }
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
