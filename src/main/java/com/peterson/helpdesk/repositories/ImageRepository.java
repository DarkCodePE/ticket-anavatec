package com.peterson.helpdesk.repositories;

import com.peterson.helpdesk.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByProduct_Id(Integer productId);
    Optional<Image> findByName(String name);
}
