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

        // Codificar la contraseña
        String encodedPassword = passwordEncoder.encode(registroUsuarioDTO.clave());
        Usuario usuario = new Usuario(
                registroUsuarioDTO.nombre(),
                registroUsuarioDTO.username(),
                registroUsuarioDTO.email(),
                encodedPassword,
                true // Usuario activo por defecto
        );
        usuarioRepository.save(usuario);

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getUsername(),
                encodedPassword // Proporcionar la contraseña codificada en clave
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
        Usuario usuario = usuarioRepository.findById(id) .orElseThrow(() -> new ValidacionDeIntegridad("El usuario con el ID proporcionado no existe."));
        usuario.setActivo(false); usuarioRepository.save(usuario);
        return new RegistroUsuarioDTO(
                usuario.getId(), usuario.getNombre(), usuario.getUsername(), usuario.getEmail(), usuario.getPassword() );
    }
}
