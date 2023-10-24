package com.peterson.helpdesk.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.peterson.helpdesk.domain.Tecnico;

import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
    Optional<Tecnico> findByEmail(String email);
}
