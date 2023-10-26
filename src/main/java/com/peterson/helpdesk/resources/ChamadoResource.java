package com.peterson.helpdesk.resources;

import com.peterson.helpdesk.domain.Chamado;
import com.peterson.helpdesk.domain.dtos.ChamadoDTO;
import com.peterson.helpdesk.domain.dtos.ChamadoExpiredDTO;
import com.peterson.helpdesk.domain.dtos.TopDTO;
import com.peterson.helpdesk.services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {

    @Autowired
    private ChamadoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
        Chamado obj = service.findById(id);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        List<Chamado> list = service.findAll();
        List<ChamadoDTO> listDTO = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO obj) {
        Chamado newObj = service.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDTO) {
        Chamado newObj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ChamadoDTO(newObj));
    }
    @GetMapping(value = "/expired")
    public ResponseEntity<List<ChamadoExpiredDTO>> findExpired() {
        List<ChamadoExpiredDTO> list = service.chamadoExpired();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/top")
    public ResponseEntity<TopDTO> findTop() {
        TopDTO list = service.topTecnicoByChamados();
        return ResponseEntity.ok().body(list);
    }
}
