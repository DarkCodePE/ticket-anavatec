package com.peterson.helpdesk.resources;

import com.peterson.helpdesk.domain.Profile;
import com.peterson.helpdesk.domain.Tecnico;
import com.peterson.helpdesk.domain.dtos.ProfileRequestDTO;
import com.peterson.helpdesk.domain.dtos.TecnicoDTO;
import com.peterson.helpdesk.services.TecnicoService;
import com.peterson.helpdesk.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/tecnicos")
public class TecnicoResource {

    // localhost:8080/tecnicos

    @Autowired
    private TecnicoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico obj = this.service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }
    @GetMapping(value = "/email")
    public ResponseEntity<TecnicoDTO> findByEmail(@RequestParam String email) {
        Tecnico obj = this.service.findByEmail(email);
        if (obj.getProfile() != null){
            obj.getProfile().setAvatar(ImageUtil.decompressImage(obj.getProfile().getAvatar()));
        }
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
         return ResponseEntity.ok().body(listDTO);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<Profile> findProfile(@RequestParam Integer id) {
        Profile profile = service.findProfileById(id);
        return ResponseEntity.ok().body(profile);
    }
    @PostMapping(value = "/profile")
    public ResponseEntity<Profile> createProfile(@Valid @RequestBody ProfileRequestDTO requestDTO) {
        Profile newProfile = service.saveProfile(requestDTO);
        return ResponseEntity.ok().body(newProfile);
    }
    @PutMapping(value = "/profile/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Integer id, @RequestBody ProfileRequestDTO requestDTO) {
        Profile newProfile = service.updateProfile(id, requestDTO);
        return ResponseEntity.ok().body(newProfile);
    }
    @PostMapping(value = "/upload/avatar")
    public ResponseEntity<TecnicoDTO> updateProfileAvatar(@RequestPart String email, @RequestPart MultipartFile file) throws IOException {
        Tecnico newProfile = service.uploadAvatar(email, file);
        if (newProfile.getProfile() != null){
            newProfile.getProfile().setAvatar(ImageUtil.decompressImage(newProfile.getProfile().getAvatar()));
        }
        return ResponseEntity.ok().body(new TecnicoDTO(newProfile));
    }
    @GetMapping(value = "/profile/email")
    public ResponseEntity<Profile> findProfileByEmail(@RequestParam String email) {
        Profile profile = service.getProfileByTechnicianEmail(email);
        profile.setAvatar(ImageUtil.decompressImage(profile.getAvatar()));
        return ResponseEntity.ok().body(profile);
    }
}
