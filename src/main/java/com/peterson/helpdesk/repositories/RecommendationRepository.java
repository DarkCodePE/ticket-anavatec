package com.peterson.helpdesk.repositories;

import com.peterson.helpdesk.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {
    List<Recommendation> findBySolution_Id(Integer id);
    List<Recommendation> findAllBySolution_Chamado_Id(Integer ticketId);

    List<Recommendation> findAllBySolution_Chamado_Product_Id(Integer productId);
}