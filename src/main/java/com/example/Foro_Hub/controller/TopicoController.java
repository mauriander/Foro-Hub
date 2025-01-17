package com.example.Foro_Hub.controller;

import com.example.Foro_Hub.domain.topico.*;
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
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid TopicoDTO topicoDTO) throws ValidacionDeIntegridad {
        var topicoRegistrado = topicoService.topicoCreado(topicoDTO);
        return ResponseEntity.ok(topicoRegistrado);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ListarTopicosDTO>> listarTopicos(@PageableDefault(size = 10) Pageable paged) {
        return ResponseEntity.ok(topicoRepository.findByActivoTrue((org.springframework.data.domain.Pageable) paged).map(ListarTopicosDTO::new));
    }

    @PutMapping("/actualizar/{id}")
    @Transactional
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoActualizadoDTO topicoActualizadoDTO) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.actualizarTopico(topicoActualizadoDTO);
        return ResponseEntity.ok(new RespuestaTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getNombreCurso(),
                topico.getFecha()));
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopicoDTO> obtenerTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var topicoId = new RespuestaTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getNombreCurso(),
                topico.getFecha());
        return ResponseEntity.ok(topicoId);
    }
}
