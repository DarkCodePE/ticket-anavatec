package com.peterson.helpdesk.repositories;

import com.peterson.helpdesk.domain.Chamado;
import com.peterson.helpdesk.domain.Product;
import com.peterson.helpdesk.domain.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution, Integer> {
    List<Solution> findByChamado_Id(Integer ticketId);
    Boolean existsByChamado_Id(Integer ticketId);
    List<Solution> findByChamado_Product_Id(Integer productId);
}
