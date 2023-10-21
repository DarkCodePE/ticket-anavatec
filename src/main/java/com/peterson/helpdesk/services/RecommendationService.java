package com.peterson.helpdesk.services;

import com.peterson.helpdesk.domain.Recommendation;
import com.peterson.helpdesk.domain.Solution;
import com.peterson.helpdesk.domain.dtos.RecommendationDTO;
import com.peterson.helpdesk.repositories.RecommendationRepository;
import com.peterson.helpdesk.repositories.SolutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final SolutionRepository solutionRepository;

    public RecommendationService(RecommendationRepository recommendationRepository, SolutionRepository solutionRepository) {
        this.recommendationRepository = recommendationRepository;
        this.solutionRepository = solutionRepository;
    }

    public Recommendation saveRecommendation(RecommendationDTO recommendation) {
      /*  Solution solution = solutionRepository.findById(recommendation.getSolutionId()).orElseThrow(() -> new RuntimeException("Solution not found"));*/
       /* return recommendationRepository.save(Recommendation.builder()
                .description(recommendation.getDescription())
                .solution(solution)
                .build());*/
        return null;
    }

    public Recommendation getRecommendationById(Integer id) {
        return recommendationRepository.findById(id).orElse(null);
    }
    public Recommendation getRecommendationBySolutionId(Integer id) {
        return null;
    }
    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

    public void deleteRecommendationById(Integer id) {
        recommendationRepository.deleteById(id);
    }
}