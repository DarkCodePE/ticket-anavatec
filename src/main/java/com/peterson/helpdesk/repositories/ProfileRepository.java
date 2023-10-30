package com.peterson.helpdesk.repositories;

import com.peterson.helpdesk.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByTecnico_Id(Integer id);
}
