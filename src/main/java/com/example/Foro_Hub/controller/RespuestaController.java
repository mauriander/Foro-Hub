package com.example.Foro_Hub.controller;

import com.example.Foro_Hub.domain.respuesta.*;
import com.example.Foro_Hub.domain.topico.TopicoRepository;
import com.example.Foro_Hub.domain.usuario.UsuarioRepository;
import com.example.Foro_Hub.infra.errores.ValidacionDeIntegridad;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@ResponseBody
@RequestMapping("/respuesta")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<?> registrarRespuesta(@RequestBody @Valid RespuestaDTO respuestaDTO) throws ValidacionDeIntegridad {
        // Crear respuesta
        var respuestaRegistrada = respuestaService.respuestaCreadaDTO(respuestaDTO);
        return ResponseEntity.ok(respuestaRegistrada);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ListarRespuestasDTO>> listarRespuestas(@PageableDefault(size = 10) Pageable paged) {
        return ResponseEntity.ok(respuestaRepository.findByActivoTrue(paged).map(ListarRespuestasDTO::new));
    }

    @PutMapping("/actualizar/{id}")
    @Transactional
    public ResponseEntity<?> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid RespuestaActualizadaDTO respuestaActualizadaDTO) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarRespuesta(respuestaActualizadaDTO);
        return ResponseEntity.ok(new RespuestaDTO(
                respuesta.getSolucion(),
                respuesta.getAutor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getActivo(),
                respuesta.getFechaCreacion()
        ));
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<?> eliminarRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.desactivarRespuesta();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> obtenerRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        var respuestaId = new RespuestaDTO(
                respuesta.getSolucion(),
                respuesta.getAutor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getActivo(),
                respuesta.getFechaCreacion()
        );
        return ResponseEntity.ok(respuestaId);
    }
}
