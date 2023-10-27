package com.peterson.helpdesk.repositories;

import com.peterson.helpdesk.domain.Chamado;
import com.peterson.helpdesk.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
    List<Chamado> findByProduct_Id(Integer productId);
    List<Chamado> findByTecnico_Id(Integer id);
    @Query("SELECT c FROM Chamado c WHERE " +
            "c.titulo LIKE CONCAT('%',:query, '%')" +
            "Or c.observacoes LIKE CONCAT('%', :query, '%')"+
            "AND c.product.id = :id")
    List<Chamado> searchChamados(String query, Integer id);

}
