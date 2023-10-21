package com.peterson.helpdesk.repositories;

import com.peterson.helpdesk.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {
    //Optional<Recommendation> findBySolution_Id(Integer id);
}