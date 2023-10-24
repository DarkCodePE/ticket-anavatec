package com.peterson.helpdesk.repositories;

import com.peterson.helpdesk.domain.Chamado;
import com.peterson.helpdesk.domain.Product;
import com.peterson.helpdesk.domain.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution, Integer> {
    List<Solution> findByChamado_Id(Integer ticketId);
    Boolean existsByChamado_Id(Integer ticketId);
    @Query("SELECT s FROM Solution s WHERE " +
            "s.title LIKE CONCAT('%',:query, '%')" +
            "Or s.summary LIKE CONCAT('%', :query, '%')"+
            "AND s.chamado.product.id = :id")
    List<Solution> searchSolutions(String query, Integer id);
    List<Solution> findByChamado_Product_Id(Integer productId);
}
