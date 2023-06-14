package com.alura.controladores;

import com.alura.dto.Respuesta;
import com.alura.modelo.Topico;
import com.alura.modelo.Usuario;
import com.alura.repositorios.RespuestaRepository;
import com.alura.repositorios.TopicoRepository;
import com.alura.repositorios.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    public RespuestaController(RespuestaRepository respuestaRepository, TopicoRepository topicoRepository, UsuarioRepository usuarioRepository) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<Respuesta> createRespuesta(@RequestBody com.alura.modelo.Respuesta respuesta) {
        Topico topico = respuesta.getTopico();
        Usuario autor = respuesta.getAutor();
        if (topico == null || topico.getId() == null || autor == null || autor.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        topico = topicoRepository.findById(topico.getId()).orElse(null);
        autor = usuarioRepository.findById(autor.getId()).orElse(null);

        if (topico == null || autor == null) {
            return ResponseEntity.badRequest().build();
        }

        respuesta.setTopico(topico);
        respuesta.setAutor(autor);

        com.alura.modelo.Respuesta nuevaRespuesta = respuestaRepository.save(respuesta);

        Respuesta nuevaRespuestaDTO = new Respuesta();
        nuevaRespuestaDTO.setId(nuevaRespuesta.getId());
        nuevaRespuestaDTO.setMensaje(nuevaRespuesta.getMensaje());
        nuevaRespuestaDTO.setTopicoId(nuevaRespuesta.getTopico().getId());
        nuevaRespuestaDTO.setNombreAutor(nuevaRespuesta.getAutor().getNombre());

        return ResponseEntity.ok(nuevaRespuestaDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Respuesta> updateRespuesta(@PathVariable Long id, @RequestBody Respuesta updatedRespuestaDTO) {
        com.alura.modelo.Respuesta respuesta = respuestaRepository.findById(id).orElse(null);
        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizar los datos de la respuesta
        respuesta.setMensaje(updatedRespuestaDTO.getMensaje());

        com.alura.modelo.Respuesta respuestaActualizada = respuestaRepository.save(respuesta);

        // Crear una instancia de RespuestaDTO con los datos actualizados
        Respuesta respuestaDTO = new Respuesta();
        respuestaDTO.setId(respuestaActualizada.getId());
        respuestaDTO.setMensaje(respuestaActualizada.getMensaje());
        respuestaDTO.setTopicoId(respuestaActualizada.getTopico().getId());
        respuestaDTO.setNombreAutor(respuestaActualizada.getAutor().getNombre());

        return ResponseEntity.ok(respuestaDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRespuesta(@PathVariable Long id) {
        com.alura.modelo.Respuesta respuesta = respuestaRepository.findById(id).orElse(null);
        if (respuesta == null) {
            // Manejar el caso en el que no se encuentre la respuesta
            // Por ejemplo, lanzar una excepción o devolver un error
            return ResponseEntity.notFound().build();
        }

        respuestaRepository.delete(respuesta);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> getRespuestaById(@PathVariable Long id) {
        com.alura.modelo.Respuesta respuesta = respuestaRepository.findById(id).orElse(null);
        if (respuesta == null) {

            return ResponseEntity.notFound().build();
        }

        Respuesta respuestaDTO = new Respuesta();
        respuestaDTO.setId(respuesta.getId());
        respuestaDTO.setMensaje(respuesta.getMensaje());
        respuestaDTO.setTopicoId(respuesta.getTopico().getId());
        respuestaDTO.setNombreAutor(respuesta.getAutor().getNombre());



        return ResponseEntity.ok(respuestaDTO);
    }
}