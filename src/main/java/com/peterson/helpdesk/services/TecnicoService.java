package com.peterson.helpdesk.services;

import com.peterson.helpdesk.domain.Pessoa;
import com.peterson.helpdesk.domain.Profile;
import com.peterson.helpdesk.domain.dtos.ProfileRequestDTO;
import com.peterson.helpdesk.domain.dtos.TecnicoDTO;
import com.peterson.helpdesk.repositories.PessoaRepository;
import com.peterson.helpdesk.repositories.ProfileRepository;
import com.peterson.helpdesk.repositories.TecnicoRepository;
import com.peterson.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.peterson.helpdesk.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.peterson.helpdesk.domain.Tecnico;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! id: " + id));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        Profile profile = new Profile();
        profile.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newObj.setProfile(profile);
        return repository.save(newObj);
    }
    public Tecnico findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! email: " + email));
    }

    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);

        if(!objDTO.getSenha().equals(oldObj.getSenha())) {
            objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        }

        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if(obj.getChamados().size() > 0){
            throw new DataIntegrityViolationException("Tecnico possui ordens de serviço e não pode ser deletado!");
        }
        repository.deleteById(id);

    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }

    //getProfileById
    public Profile findProfileById(Integer id) {
        return profileRepository.findByTecnico_Id(id).orElseThrow(() -> new ObjectnotFoundException("Tecnico es no encontrado! id: " + id));
    }
    //saveProfile
    public Profile saveProfile(ProfileRequestDTO obj) {
        Profile profile = new Profile();
        profile.setTecnico(repository.getReferenceById(obj.getTecnicoId()));
        profile.setAddress(obj.getAddress());
        profile.setResume(obj.getResume());
        profile.setPhone(obj.getPhone());
        profile.setBirthDate(obj.getBirthDate());
        profile.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return profileRepository.save(profile);
    }
    //updateProfile
    public Profile updateProfile(Integer id, ProfileRequestDTO obj) {
        Profile profile = profileRepository.findByTecnico_Id(id).orElseThrow(() -> new ObjectnotFoundException("Tecnico es no encontrado! id: " + id));
        profile.setAddress(obj.getAddress());
        profile.setResume(obj.getResume());
        profile.setPhone(obj.getPhone());
        profile.setBirthDate(obj.getBirthDate());
        return profileRepository.save(profile);
    }
}
