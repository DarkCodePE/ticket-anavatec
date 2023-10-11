package com.peterson.helpdesk.repositories;

import com.peterson.helpdesk.domain.Chamado;
import com.peterson.helpdesk.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
    List<Chamado> findByProduct_Id(Integer productId);
}
