package com.alura.controladores;


import com.alura.dto.Estatus;
import com.alura.modelo.Curso;
import com.alura.modelo.Respuesta;
import com.alura.modelo.Usuario;
import com.alura.dto.Topico;
import com.alura.repositorios.CursoRepository;
import com.alura.repositorios.RespuestaRepository;
import com.alura.repositorios.TopicoRepository;
import com.alura.repositorios.UsuarioRepository;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final RespuestaRepository respuestaRepository;


    public TopicoController(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository, RespuestaRepository respuestaRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.respuestaRepository = respuestaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Topico>> getAllTopicos() {
        List<com.alura.modelo.Topico> topicos = topicoRepository.findAll();

        List<Topico> topicosDTO = new ArrayList<>();

        for (com.alura.modelo.Topico topico : topicos) {
            Topico topicoDTO = new Topico();
            topicoDTO.setId(topico.getId());
            topicoDTO.setTitulo(topico.getTitulo());
            topicoDTO.setMensaje(topico.getMensaje());
            topicoDTO.setFechaCreacion(topico.getFechaCreacion());
            topicoDTO.setStatus(topico.getStatus().name());
            topicoDTO.setAutor(topico.getAutor().getNombre());
            topicoDTO.setCurso(topico.getCurso().getNombre());

            if (!topico.getRespuestas().isEmpty()) {
                Respuesta respuesta = topico.getRespuestas().get(0); // Obtén la primera respuesta
                topicoDTO.setRespuesta(respuesta.getMensaje());
            }

            topicosDTO.add(topicoDTO);
        }

        return ResponseEntity.ok(topicosDTO);
    }
    @PostMapping
    public ResponseEntity<?> createTopico(@RequestBody com.alura.modelo.Topico topico) {
        // Validar campos obligatorios
        if (StringUtils.isEmpty(topico.getTitulo()) || StringUtils.isEmpty(topico.getMensaje())) {
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios");
        }

        // Verificar si el tópico ya existe
        if (existeTopicoExcepto(topico.getTitulo(), topico.getMensaje(), null)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El tópico ya existe");
        }

        // Obtener el autor y curso desde la base de datos
        Usuario autor = obtenerUsuario(topico.getAutor().getId());
        if (autor == null) {
            return ResponseEntity.badRequest().body("El usuario no existe");
        }

        Curso curso = obtenerCurso(topico.getCurso().getId());
        if (curso == null) {
            return ResponseEntity.badRequest().body("El curso no existe");
        }

        // Asignar el autor y curso al tópico
        topico.setAutor(autor);
        topico.setCurso(curso);

        // Registrar el nuevo tópico
        com.alura.modelo.Topico nuevoTopico = topicoRepository.save(topico);

        Topico topicoDTO = new Topico();
        topicoDTO.setId(nuevoTopico.getId());
        topicoDTO.setTitulo(nuevoTopico.getTitulo());
        topicoDTO.setMensaje(nuevoTopico.getMensaje());
        topicoDTO.setFechaCreacion(nuevoTopico.getFechaCreacion());
        topicoDTO.setStatus(nuevoTopico.getStatus().name());
        topicoDTO.setAutor(nuevoTopico.getAutor().getNombre());
        topicoDTO.setCurso(nuevoTopico.getCurso().getNombre());

        return ResponseEntity.status(HttpStatus.CREATED).body(topicoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTopico(@PathVariable Long id, @RequestBody Topico topico) {
        // Verificar si el tópico existe
        com.alura.modelo.Topico topicoExistente = obtenerTopico(id);
        if (topicoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Validar campos obligatorios
        if (StringUtils.isEmpty(topico.getTitulo()) || StringUtils.isEmpty(topico.getMensaje())) {
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios");
        }

        // Verificar si el título y mensaje del tópico ya existen en otro tópico
        if (existeTopicoExcepto(topico.getTitulo(), topico.getMensaje(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El tópico ya existe");
        }

        // Actualizar los datos del tópico existente
        topicoExistente.setTitulo(topico.getTitulo());
        topicoExistente.setMensaje(topico.getMensaje());

        // Guardar los cambios en el tópico existente
        com.alura.modelo.Topico topicoActualizado = topicoRepository.save(topicoExistente);

        // Crear un objeto TopicoDTO actualizado y devolverlo como respuesta
        Topico topicoDTOActualizado = new Topico();
        topicoDTOActualizado.setId(topicoActualizado.getId());
        topicoDTOActualizado.setTitulo(topicoActualizado.getTitulo());
        topicoDTOActualizado.setMensaje(topicoActualizado.getMensaje());
        topicoDTOActualizado.setFechaCreacion(topicoActualizado.getFechaCreacion());
        topicoDTOActualizado.setStatus(topicoActualizado.getStatus().name());
        topicoDTOActualizado.setAutor(topicoActualizado.getAutor().getNombre());
        topicoDTOActualizado.setCurso(topicoActualizado.getCurso().getNombre());

        return ResponseEntity.ok(topicoDTOActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        // Verificar si el tópico existe
        com.alura.modelo.Topico topicoExistente = obtenerTopico(id);
        if (topicoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Eliminar el tópico
        topicoRepository.delete(topicoExistente);
        return ResponseEntity.ok().build();
    }

    private boolean existeTopicoExcepto(String titulo, String mensaje, Long id) {
        return topicoRepository.existsByTituloAndMensajeAndIdNot(titulo, mensaje, id);
    }

    private Usuario obtenerUsuario(Long id) {
        if (id == null) {
            return null;
        }
        return usuarioRepository.findById(id).orElse(null);
    }

    private Curso obtenerCurso(Long id) {
        return cursoRepository.findById(id)
                .orElse(null);
    }

    private com.alura.modelo.Topico obtenerTopico(Long id) {
        return topicoRepository.findById(id)
                .orElse(null);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Topico> updateTopicoStatus(@PathVariable Long id, @RequestBody Estatus updateStatusDTO) {
        com.alura.modelo.Topico topico = topicoRepository.findById(id).orElse(null);
        if (topico == null) {
            return ResponseEntity.notFound().build();
        }

        topico.setStatus(updateStatusDTO.getStatus());
        com.alura.modelo.Topico topicoActualizado = topicoRepository.save(topico);

        Topico topicoDTO = new Topico();
        topicoDTO.setId(topicoActualizado.getId());
        topicoDTO.setTitulo(topicoActualizado.getTitulo());
        topicoDTO.setMensaje(topicoActualizado.getMensaje());
        topicoDTO.setFechaCreacion(topicoActualizado.getFechaCreacion());
        topicoDTO.setStatus(topicoActualizado.getStatus().name());
        topicoDTO.setAutor(topicoActualizado.getAutor().getNombre());
        topicoDTO.setCurso(topicoActualizado.getCurso().getNombre());
        topicoDTO.setRespuesta(topicoActualizado.getMensaje());

        return ResponseEntity.ok(topicoDTO);
    }

}
